package com.example.takeeat.ui.refrigerator

import android.Manifest
import android.app.Activity
import android.app.Application
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.icu.text.SimpleDateFormat
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.takeeat.BuildConfig
import com.example.takeeat.R
import com.example.takeeat.databinding.FragmentRefrigeratorBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton
import org.json.JSONArray
import org.json.JSONObject
import java.io.*
import java.net.HttpURLConnection
import java.net.URL
import java.util.*
import kotlin.collections.ArrayList

class RefrigeratorFragment : Fragment() {

    private var _binding: FragmentRefrigeratorBinding? = null


    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    lateinit var mainFab:FloatingActionButton
    lateinit var directFab:FloatingActionButton
    lateinit var galleryFab:FloatingActionButton
    lateinit var cameraFab:FloatingActionButton
    lateinit var directText:TextView
    lateinit var galleryText:TextView
    lateinit var cameraText:TextView
    lateinit var currentPhotoPath: String

    var isFabOpen = false
    val CAMERA = arrayOf(Manifest.permission.CAMERA)
    val STORAGE = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
    val CAMERA_CODE = 98
    val STORAGE_CODE = 99
    val GALLERY_CODE = 200
    var ocrApiGwUrl:String = BuildConfig.OCR_API_GW_URL
    var ocrSecretKey:String = BuildConfig.OCR_SECRETKEY



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val refrigeratorViewModel =
            ViewModelProvider(this).get(RefrigeratorViewModel::class.java)

        _binding = FragmentRefrigeratorBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textRefrigerator
        refrigeratorViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        mainFab= binding.refrigeratorfab
        directFab = binding.directSubFab
        galleryFab = binding.gallerySubFab
        cameraFab = binding.cameraSubFab
        directText = binding.textDirect
        galleryText = binding.textGallery
        cameraText = binding.textCamera
        val fabOpen = AnimationUtils.loadAnimation(context, R.anim.fab_open)
        val fabClose = AnimationUtils.loadAnimation(context, R.anim.fab_close)
        val rotateForward = AnimationUtils.loadAnimation(context, R.anim.rotate_forward)
        val rotateBackward = AnimationUtils.loadAnimation(context, R.anim.rotate_backward)


        mainFab.setOnClickListener (View.OnClickListener {
            if(isFabOpen){
                mainFab.startAnimation(rotateForward)
                directFab.startAnimation(fabClose)
                galleryFab.startAnimation(fabClose)
                cameraFab.startAnimation(fabClose)
                directText.startAnimation(fabClose)
                galleryText.startAnimation(fabClose)
                cameraText.startAnimation(fabClose)
                directFab.hide()
                directText.isVisible = false
                directFab.isClickable = false
                galleryFab.hide()
                galleryText.isVisible = false
                galleryFab.isClickable = false
                cameraFab.hide()
                cameraText.isVisible = false
                cameraFab.isClickable = false
                isFabOpen=false
            }
            else{
                mainFab.startAnimation(rotateBackward)
                directFab.startAnimation(fabOpen)
                galleryFab.startAnimation(fabOpen)
                cameraFab.startAnimation(fabOpen)
                directText.startAnimation(fabOpen)
                galleryText.startAnimation(fabOpen)
                cameraText.startAnimation(fabOpen)
                directFab.show()
                directText.isVisible = true
                directFab.isClickable = true
                galleryFab.show()
                galleryText.isVisible = true
                galleryFab.isClickable = true
                cameraFab.show()
                cameraText.isVisible = true
                cameraFab.isClickable = true
                isFabOpen=true

            }
        })
        directFab.setOnClickListener (View.OnClickListener {
            val intent = Intent(getActivity(), AddRefrigeratorActivity::class.java)
            var nonOcr = ArrayList<RefItem>()
            nonOcr.add(RefItem(null,null,null,null,null))
            intent.putExtra("OCR_RESULT",nonOcr)
            startActivity(intent)
        })
        galleryFab.setOnClickListener (View.OnClickListener {
            callImage(GALLERY_CODE)
        })

        cameraFab.setOnClickListener (View.OnClickListener {
            callImage(CAMERA_CODE)
        })


        return root
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun callImage(taskCode:Int){
        if (checkPermission(CAMERA+STORAGE, CAMERA_CODE)) {
            when(taskCode){
                GALLERY_CODE -> {
                    var intent = Intent(Intent.ACTION_PICK).apply {
                    setDataAndType(
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        "image/*"
                    )
                    startActivityForResult(this, GALLERY_CODE)
                    }
                }
                CAMERA_CODE ->{
                    cameraCall()

                }
            }
        }
    }



    fun checkPermission(permissions: Array<out String>, type: Int): Boolean
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            for (permission in permissions) {
                Log.d("Response","CheckPermissionstart"+permission)
                if (ContextCompat.checkSelfPermission(context as Activity, permission) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(context as Activity, permissions, type)
                    Log.d("Response","Permissionfailed")
                    return false;
                }
            }
        }

        return true;
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode) {
            CAMERA_CODE -> {
                for (grant in grantResults) {
                    if (grant != PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(context, "카메라 권한을 승인해 주세요.", Toast.LENGTH_LONG).show()
                    }
                }
            }
            STORAGE_CODE -> {
                for (grant in grantResults) {
                    if (grant != PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(context, "저장소 권한을 승인해 주세요.", Toast.LENGTH_LONG).show()
                        //finish() 앱을 종료함
                    }
                }
            }
        }
    }



    @Throws(IOException::class)
    private fun createImageFile(): File {
        // Create an image file name
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File? = activity?.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            "JPEG_${timeStamp}_", /* prefix */
            ".jpeg", /* suffix */
            storageDir /* directory */
        ).apply {
            // Save a file: path for use with ACTION_VIEW intents
            currentPhotoPath = absolutePath
        }
    }
    private fun cameraCall() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        // Ensure that there's a camera activity to handle the intent
        if (context?.let { takePictureIntent.resolveActivity(it.packageManager) } != null) {
            // Create the File where the photo should go
            var photoFile: File? = null
            try {
                photoFile = createImageFile()
            }
            catch (ex: IOException) { // 파일을 만드는데 오류가 발생한 경우
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                val photoURI: Uri = FileProvider.getUriForFile(requireContext(),BuildConfig.APPLICATION_ID +".provider",photoFile)
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                startActivityForResult(takePictureIntent, CAMERA_CODE)
            }
        }
    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        //findViewById(R.id.result_image).setImageURI(photoUri);
        lateinit var imgUri:Uri
        lateinit var img:Bitmap
        try {
            when (requestCode) {

                GALLERY_CODE ->{
                    if (resultCode === Activity.RESULT_OK) {
                        if (data != null) {
                            imgUri = data.getData()!!
                            img = MediaStore.Images.Media
                                .getBitmap(activity?.contentResolver, imgUri)



                        };
                    }
                    else{
                        Toast.makeText(context, "사진을 다시 선택해주세요", Toast.LENGTH_LONG).show()
                    }

                }
                CAMERA_CODE -> {
                    if (resultCode === Activity.RESULT_OK) {
                        val file = File(currentPhotoPath)
                        img = MediaStore.Images.Media.getBitmap(activity?.contentResolver, Uri.fromFile(file))

                    }
                    else{
                        Toast.makeText(context, "사진을 다시 촬영해주세요", Toast.LENGTH_LONG).show()
                    }
                }

            }

            val base64encodedimg = bitmapto64(img)
            val handler = Handler()
            Thread(Runnable{
                val ocrResult = doOcr(base64encodedimg)
                
                handler.post{
                    if(ocrResult != null) {
                        val intent = Intent(getActivity(), AddRefrigeratorActivity::class.java)
                        intent.putExtra("OCR_RESULT",ocrResult)
                        startActivity(intent)
                    }
                    else{
                        Toast.makeText(context, "오류가 발생했습니다 다시 시도해주세요", Toast.LENGTH_LONG).show()
                    }

                }


            }).start()
        } catch (error: Exception) {
            error.printStackTrace()
        }
    }


    fun bitmapto64(tbitmap: Bitmap):String{
        var bitmap: Bitmap = scaleBitmapDown(tbitmap,640)
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
        val imageBytes: ByteArray = byteArrayOutputStream.toByteArray()
        val base64encoded = Base64.encodeToString(imageBytes, Base64.NO_WRAP)
        return base64encoded
    }

    private fun scaleBitmapDown(bitmap: Bitmap, maxDimension: Int): Bitmap {
        val originalWidth = bitmap.width
        val originalHeight = bitmap.height
        var resizedWidth = maxDimension
        var resizedHeight = maxDimension
        if (originalHeight > originalWidth) {
            resizedHeight = maxDimension
            resizedWidth =
                (resizedHeight * originalWidth.toFloat() / originalHeight.toFloat()).toInt()
        } else if (originalWidth > originalHeight) {
            resizedWidth = maxDimension
            resizedHeight =
                (resizedWidth * originalHeight.toFloat() / originalWidth.toFloat()).toInt()
        } else if (originalHeight == originalWidth) {
            resizedHeight = maxDimension
            resizedWidth = maxDimension
        }
        return Bitmap.createScaledBitmap(bitmap, resizedWidth, resizedHeight, false)
    }


    fun doOcr(base64img:String):ArrayList<RefItem>?{
        try {
            val url: URL = URL(ocrApiGwUrl)
            var con: HttpURLConnection = url.openConnection() as HttpURLConnection
            con.setUseCaches(false)
            con.setDoInput(true)
            con.setDoOutput(true)
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json; charset=utf-8")
            con.setRequestProperty("X-OCR-SECRET", ocrSecretKey)
            Log.d("Response",ocrApiGwUrl)
            Log.d("Response",ocrSecretKey as String)

            var json = JSONObject()
            json.put("version", "V2")
            json.put("requestId", UUID.randomUUID().toString())
            json.put("timestamp", System.currentTimeMillis())
            var image = JSONObject()
            image.put("format", "jpeg")
            image.put("data", base64img)
            image.put("name", "test")
            var images = JSONArray()
            images.put(image)
            json.put("images", images)
            var requestBody = json.toString()
            val wr = DataOutputStream(con.getOutputStream())
            wr.writeBytes(requestBody)
            wr.flush()
            wr.close()

            var responseCode = con.getResponseCode()
            val br: BufferedReader
            if (responseCode == 200) {
                br = BufferedReader(InputStreamReader(con.getInputStream()))
                //Log.d("Response","Success"+br.readLine())
            } else {
                br = BufferedReader(InputStreamReader(con.getErrorStream()))
                Log.d("Response","fail")
                return null
            }
            Log.d("Response","?")
            var resultJson= JSONObject(br.readLine())
            Log.d("Response","why error?")
            var resultImage = resultJson.getJSONArray("images")
            Log.d("Response", resultImage.toString())
            var resultField = resultImage.getJSONObject(0).getJSONObject("receipt").getJSONObject("result").getJSONArray("subResults").getJSONObject(0).getJSONArray("items")

            //var inputLine:String = br.readLine()
            Log.d("Response",resultField.toString())
            Log.d("Response","??")
            val resultSize = resultField.length()
            Log.d("Response","FieldSize"+resultSize.toString())
            var response  = ArrayList<RefItem>()
            for(i in 0 until resultSize){
                resultJson = resultField.getJSONObject(i)
                val itemName = resultJson.getJSONObject("name").getJSONObject("formatted").getString("value")
                val itemAmount = resultJson.getJSONObject("count").getJSONObject("formatted").getInt("value")
                response.add(RefItem(itemName,null,null,itemAmount,null))
            }
            br.close()
            Log.d("Response",response.toString())
            return response
        }catch(e:Exception){
            Log.d("Response","Exception" + e.toString())
            //Toast.makeText(context, "오류가 발생했습니다 다시 시도해주세요", Toast.LENGTH_LONG).show()
            return null
        }

    }

}