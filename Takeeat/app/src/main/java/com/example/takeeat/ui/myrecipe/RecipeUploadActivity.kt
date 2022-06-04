package com.example.takeeat.ui.myrecipe

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.provider.MediaStore
import android.util.Log
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import com.amazonaws.mobile.client.AWSMobileClient
import com.example.takeeat.databinding.ActivityRecipeUploadBinding
import com.example.takeeat.launchWhenStarted
import com.example.takeeat.ui.myrecipe.S3UploadUtils.getS3ImageURL
import com.example.takeeat.ui.myrecipe.adapter.AddedMaterialAdapter
import com.example.takeeat.ui.myrecipe.adapter.RecipeOrderListAdapter
import com.example.takeeat.ui.myrecipe.data.Recipe
import org.json.JSONObject
import java.io.BufferedReader
import java.io.DataOutputStream
import java.io.File
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder


class RecipeUploadActivity: AppCompatActivity() {
    private var failedPermissions = ArrayList<String>()
    lateinit var binding: ActivityRecipeUploadBinding
    private val viewModel: RecipeUploadViewModel by viewModels()
    private var cameraUri: Uri? = null
    private var imageFilePath: String? = null


    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        binding = ActivityRecipeUploadBinding.inflate(layoutInflater)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        setContentView(binding.root)

        addMaterial()
        addRecipeThumbnail()
        addCookingOrder()
        setCookingOrderList()
        setAddedMaterialList()
        showSelectMaterialDialog()
        upload()
    }

    private fun addMaterial() {
        with(viewModel) {
            binding.buttonAddMaterial.setOnClickListener {
                addMaterial(Material(tag.value ?: "", amount.value ?: "", unit.value ?: ""))
            }
        }
    }

    private fun showSelectMaterialDialog() {
        binding.textviewSelectTag.setOnClickListener {
            SelectMaterialDialogFragment {
                viewModel.selectMaterial(it)
            }.show(supportFragmentManager, "selectMaterial")
        }
    }

    // 재료추가 RecyclerView
    private fun setAddedMaterialList() {
        binding.recyclerviewMaterial.run {
            adapter = AddedMaterialAdapter {

                viewModel.removeMaterial(it)
            }
            viewModel.addedMaterialList.launchWhenStarted(this@RecipeUploadActivity) {

                (adapter as AddedMaterialAdapter).submitList(it)
            }
        }
    }

    // 요리 순서 recyclerview 설정
    private fun setCookingOrderList() {
        binding.recyclerviewCookOrder.run {
            adapter = RecipeOrderListAdapter(object: RecipeOrderListAdapter.ChangeContentInterface{
                override fun writeDescription(order: Order, description: String) {

                    viewModel.changeOrderDescription(order, description)
                }

                override fun addImage(order: Order) {
                    viewModel.changeIsAddToThumbnail(false)
                    viewModel.changeUploadPictureOrder(order)
                    val bottomSheet = AddPictureBottomSheetFragment(
                        galleryListener = { requestGalleryPermission.launch(PERMISSION_WRITE_EXTERNAL_STORAGE) },
                        cameraListener = { requestCameraPermission.launch(TAKE_PICTURE_PERMISSIONS) }
                    )
                    bottomSheet.show(supportFragmentManager, "add picture")
                }

                override fun deleteOrder(order: Order) {
                    viewModel.deleteCookingOrder(order)
                }
            })
            viewModel.cookingOrderList.launchWhenStarted(this@RecipeUploadActivity) {
                (adapter as RecipeOrderListAdapter).submitList(it)
            }
        }
    }

    private fun addCookingOrder() {
        binding.buttonAddRecipeOrder.setOnClickListener {
            viewModel.initUploadPictureOrder()
            viewModel.addEmptyCookingOrder()
        }
    }


    private fun addRecipeThumbnail() {
        binding.imageviewThumbnail.setOnClickListener {
            viewModel.changeIsAddToThumbnail(true)
            AddPictureBottomSheetFragment(
                galleryListener = { requestGalleryPermission.launch(PERMISSION_WRITE_EXTERNAL_STORAGE) },
                cameraListener = { requestCameraPermission.launch(TAKE_PICTURE_PERMISSIONS) }
            ).show(supportFragmentManager, "add thumbnail")
        }
    }

    private val requestCameraPermission = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
        if (permissions.values.all { it == true }) {
            takePicture()
        }
    }

    private fun takePicture() {
        val photoFile = File.createTempFile("IMG_", ".jpg", getExternalFilesDir(Environment.DIRECTORY_PICTURES))
        imageFilePath = photoFile.absolutePath
        Log.e("temp file path", "${photoFile.absolutePath}")
        val pictureUri = FileProvider.getUriForFile(this, "${packageName}.provider", photoFile)
        cameraUri = pictureUri
        cameraUri?.let { cameraActivityLauncher.launch(it) }
    }

    private val cameraActivityLauncher = registerForActivityResult(ActivityResultContracts.TakePicture()) { isSaved ->
        if (isSaved) {
            cameraUri?.let {
                viewModel.run {
                    if (addToThumbnail.value) changeThumbnailImage(it, imageFilePath) else addImage(it, imageFilePath)
                    imageFilePath = null
                }
            }
        }
    }


    private val requestGalleryPermission: ActivityResultLauncher<String> =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                val intent = Intent(Intent.ACTION_PICK)
                intent.type = MediaStore.Images.Media.CONTENT_TYPE
                intent.type = "image/*"
                fetchPicturesFromGallery.launch(intent)
            }
        }


    private val fetchPicturesFromGallery = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
        val clipData = result.data?.clipData
        val uri = result.data?.data
        when {
            uri != null -> if (viewModel.addToThumbnail.value) viewModel.changeThumbnailImage(uri,null) else viewModel.addImage(uri, null)
            clipData != null -> if(viewModel.addToThumbnail.value) viewModel.changeThumbnailImage(clipData.getItemAt(0).uri, null) else viewModel.addImage(clipData.getItemAt(0).uri, null)
        }
    }

    private fun upload() {
        binding.textviewUpload.setOnClickListener {
            with(viewModel) {
                // 라이브데이터에 저장되어 있는 uri를 s3에 업로드 후 url로 변환시켜준다.
                val thumbnailURL = if (thumbnailImage.value != null) getS3ImageURL(thumbnailImage.value!!, thumbnailFilePath.value, this@RecipeUploadActivity) else null
                // List<Object>의 경우에도 map 함수를 이용해 서버에 올라갈 DTO형식에 맞도록 변환시켜준다.
                val cookingOrderList = cookingOrderList.value.map { it.toDetailRecipe(this@RecipeUploadActivity) }

                val uploadData = Recipe(
                    recipeTitle.value.toString(),
                    thumbnailURL,
                    recipeDescription.value.toString(),
                    selectedCategory.value.toString(),
                    cookingTime.value.toString(),
                    servingCount.value.toString(),
                    difficulty.value.toString(),
                    addedMaterialList.value.map { it.toRecipeIngre() },
                    cookingOrderList
                )

                val name = URLEncoder.encode(uploadData.name, "UTF-8").replace("+", " ")
                val img = uploadData.img
                val summary = URLEncoder.encode(uploadData.summary, "UTF-8").replace("+", " ")
                val serving = URLEncoder.encode(uploadData.serving, "UTF-8").replace("+", " ")
                val time = URLEncoder.encode(uploadData.cooktime, "UTF-8").replace("+", " ")
                val category = URLEncoder.encode(uploadData.category, "UTF-8").replace("+", " ")
                val difficult = URLEncoder.encode(uploadData.difficulty, "UTF-8").replace("+", " ")
                val ingre = uploadData.ingre
                var ingretxt = ""
                val recipedata = uploadData.recipe
                var recipetxt = ""
                val author = AWSMobileClient.getInstance().username.replace("+", " ")

                var ingresearchtxt = ""
                var ingrearr : ArrayList<String> = ArrayList<String>()


                for (item in ingre) {
                    ingretxt = ingretxt +
                            "{\"ingre_name\":\"" + URLEncoder.encode(item.ingre_name, "UTF-8").replace("+", " ") + "\",\"ingre_count\":\"" + item.ingre_count + "\",\"ingre_unit\":\"" + URLEncoder.encode(item.ingre_unit, "UTF-8") + "\"},"
                    if(!ingrearr.contains(URLEncoder.encode(item.ingre_name, "UTF-8").replace("+", " "))){
                        ingrearr.add(URLEncoder.encode(item.ingre_name, "UTF-8").replace("+", " "))
                    }
                }

                if (ingretxt != "") {
                    ingretxt = ingretxt.substring(0, ingretxt.length - 1)
                }

                for (item in recipedata) {
                    recipetxt = recipetxt +
                            "{\"txt\":\"" + URLEncoder.encode(item.txt, "UTF-8").replace("+", " ") + "\",\"img\":\"" + item.img + "\"},"
                }

                if (recipetxt != "") {
                    recipetxt = recipetxt.substring(0, recipetxt.length - 1)
                }

                for (item in ingrearr) {
                    ingresearchtxt = ingresearchtxt + "\"" + item + "\","
                }

                if (ingresearchtxt != "") {
                    ingresearchtxt = ingresearchtxt.substring(0, ingresearchtxt.length - 1)
                }


                var requeststr =
                    "{\"name\":\"" + name + "\"," +
                            "\"img\" : \"" + img + "\"," +
                            "\"summary\" : \"" + summary + "\"," +
                            "\"serving\" : \"" + serving + "\"," +
                            "\"time\" : \"" + time + "\"," +
                            "\"category\" : \"" + category + "\"," +
                            "\"difficult\" : \"" + difficult + "\"," +
                            "\"ingre\" : { \"ingre_item\":[" + ingretxt + "]}," +
                            "\"recipe\" : { \"recipe_item\":[" + recipetxt + "]}," +
                            "\"author\" : \"" + author + "\"," +
                            "\"rate_num\" : 0 ," +
                            "\"rate_sum\" : 0 ," +
                            "\"ingre_search\" : [" + ingresearchtxt + "]}"

                post_recipe(requeststr)



                // 서버에 업로드 할 데이터 모아서 로그로 찍기
                Log.e("uploadData", "${uploadData}")
            }
        }
    }

    companion object {
        private const val PERMISSION_CAMERA = android.Manifest.permission.CAMERA
        private const val PERMISSION_WRITE_EXTERNAL_STORAGE = android.Manifest.permission.WRITE_EXTERNAL_STORAGE
        private val TAKE_PICTURE_PERMISSIONS = arrayOf(PERMISSION_CAMERA, PERMISSION_WRITE_EXTERNAL_STORAGE)
    }

    fun post_recipe(str : String){
        val handler = Handler()
        Thread(Runnable{


            //handler.post{
            //try {
            //AWSMobileClient.getInstance()

            val url: URL = URL("https://b62cvdj81b.execute-api.ap-northeast-2.amazonaws.com/ref-api-test/recipe")
            var conn: HttpURLConnection =url.openConnection() as HttpURLConnection
            conn.setUseCaches(false)
            conn.setRequestMethod("POST")
            conn.setRequestProperty("Content-Type", "application/json")
            conn.setRequestProperty("Connection","keep-alive")
            conn.setRequestProperty("Accept", "application/json")
            conn.setDoOutput(true)
            conn.setDoInput(true)
            //conn.connect()



            var requestBody = str


            Log.d("Response : requestBody = ",requestBody)
            val wr = DataOutputStream(conn.getOutputStream())
            wr.writeBytes(requestBody)
            wr.flush()
            wr.close()

            var responseCode = conn.getResponseCode()
            //Log.d("Response : responseCode",responseCode.toString())
            val br: BufferedReader
            if (responseCode == 200) {
                br = BufferedReader(InputStreamReader(conn.getInputStream(), "euc-kr"))
                finish()
                //Log.d("Response","Success")
            } else {
                br = BufferedReader(InputStreamReader(conn.getErrorStream(), "euc-kr"))
                //Log.d("Response","fail")
            }

            /*
            var re = br.readLine()
            var ree = JSONObject(re)
            var reee = ree.toString()
            Log.d("Response : resultJson = ",reee)
             */

            //var resultJson= JSONObject(br.readLine())
            //var rrr = br.readLine()
            //Log.d("Response : resultJson = ",resultJson.toString())

            /*
            var response  = ArrayList<RefItem>()
            val result = resultJson.get("result")
            val age = resultJson.get("age");
            val job = resultJson.get("job");
            */
            //Log.i("Response", "DATA response = " + response)

            //conn.disconnect()
            /*
            } catch (e:Exception) {
                Toast.makeText(getApplicationContext(),"데이터 전송 준비 과정 중 오류 발생",Toast.LENGTH_SHORT).show();
                Log.i("Response", "DATA FAil")
                return aff;
            }
            */

            //}





        }).start()
    }
}