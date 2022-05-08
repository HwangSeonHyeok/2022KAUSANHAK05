package com.example.takeeat.ui.refrigerator

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.amazonaws.mobile.client.AWSMobileClient
import com.example.takeeat.*
import com.example.takeeat.databinding.ActivityRefitemdetailBinding
import org.json.JSONArray
import org.json.JSONObject
import java.io.BufferedReader
import java.io.DataOutputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder
import java.util.*
import kotlin.collections.ArrayList


class RefItemDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRefitemdetailBinding
    lateinit var refItem: RefItem
    lateinit var adapter : RecipeItemAdapter
    var recipeArray:ArrayList<RecipeItem> = ArrayList<RecipeItem>()
    var calendar = Calendar.getInstance()
    var year = calendar.get(Calendar.YEAR)
    var month = calendar.get(Calendar.MONTH)
    var date = calendar.get(Calendar.DAY_OF_MONTH)
    var todayyear = calendar.get(Calendar.YEAR)
    var todaymonth = calendar.get(Calendar.MONTH)
    var todaydate = calendar.get(Calendar.DAY_OF_MONTH)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRefitemdetailBinding.inflate(layoutInflater)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        refItem = intent.getSerializableExtra("Item_Data") as RefItem
        updateUI(refItem)
        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        binding.refDetailEditButton.setOnClickListener {
            it.visibility = View.INVISIBLE
            it.isClickable = false
            binding.refDetailCompleteButton.visibility = View.VISIBLE
            binding.refDetailCompleteButton.isClickable = true
            binding.refDetailItemname.visibility = View.INVISIBLE
            binding.refDetailNameEdit.visibility = View.VISIBLE
            binding.refDetailNameEdit.setText(binding.refDetailItemname.text)
            binding.refDetailEXP.isClickable = true
            binding.refDetailTag.isClickable = true
            binding.refDetailItemAmount.visibility = View.INVISIBLE
            binding.refDetailEditAmount.visibility = View.VISIBLE
            binding.refDetailEditAmount.setText(binding.refDetailItemAmount.text)

        }
        binding.refDetailCompleteButton.setOnClickListener {
            it.visibility = View.INVISIBLE
            it.isClickable = false
            binding.refDetailEditButton.visibility = View.VISIBLE
            binding.refDetailEditButton.isClickable = true
            binding.refDetailItemname.visibility = View.VISIBLE
            binding.refDetailNameEdit.visibility = View.INVISIBLE
            refItem.itemname = binding.refDetailNameEdit.text.toString()
            binding.refDetailEXP.isClickable = false
            binding.refDetailTag.isClickable = false
            binding.refDetailItemAmount.visibility = View.VISIBLE
            binding.refDetailEditAmount.visibility = View.INVISIBLE
            refItem.itemamount = binding.refDetailEditAmount.text.toString().toInt()
            refItem.itemtag = binding.refDetailTag.text.toString()
            imm.hideSoftInputFromWindow(binding.refDetailNameEdit.windowToken, 0)
            imm.hideSoftInputFromWindow(binding.refDetailEditAmount.windowToken, 0)
            updateUI(refItem)

            //여기다 DB업데이트 코드 넣어주세요 refItem값으로 업데이트 해주면됩니다
            val sObject = JSONObject() //배열 내에 들어갈 json
            sObject.put("user_id", AWSMobileClient.getInstance().username)
            sObject.put("item_id", refItem.itemid.toString())
            sObject.put("update_name", URLEncoder.encode(refItem.itemname.toString(), "UTF-8"))
            sObject.put("update_amount", refItem.itemamount.toString())
            sObject.put("update_exdate", binding.refDetailEXP.text.toString().replace(".", "-"))
            sObject.put("update_tag", URLEncoder.encode(refItem.itemtag, "UTF-8"))
            sObject.put("update_unit", URLEncoder.encode(refItem.itemunit.toString(), "UTF-8"))


            //Log.d("Response sObjects sObject sObject sObject : ",sObject.toString())


            update_ref_item(sObject)

        }
        binding.refDetailEXP.setOnClickListener{
            val dateSelector = DatePickerDialog(this, {_, year, month, date ->
                binding.refDetailEXP.setText(year.toString() + "." + (month + 1).toString() + "." + date.toString())
                refItem.itemexp =Date(year,month,date)
            },year,month,date)
            dateSelector.show()
        }
        binding.refDetailTag.setOnClickListener{
            val tags = resources.getStringArray(R.array.RefrigeratorItemTagArray)
            val dialogBuilder = AlertDialog.Builder(this)
            dialogBuilder.setTitle("태그를 선택해주세요")
            dialogBuilder.setItems(tags) {
                    p0, p1 ->
                binding.refDetailTag.text = tags[p1]
                //refItem.itemtag = tags[p1]

            }
            val alertDialog = dialogBuilder.create()
            alertDialog.show()

        }
        binding.refDetailEXP.isClickable = false
        binding.refDetailTag.isClickable = false


        /*val JA = JSONArray("[{\"ingre_name\":\"계란\",\"ingre_num\":1,\"ingre_count\":\"5\",\"ingre_unit\":\"개\"},{\"ingre_name\":\"육수팩\",\"ingre_num\":2,\"ingre_count\":\"1\",\"ingre_unit\":\"개\"},{\"ingre_name\":\"소금\",\"ingre_num\":3,\"ingre_count\":\"\",\"ingre_unit\":\"약간\"}]")

        recipeArray.add(RecipeItem("1","집에서도 쉽게 찰떡과 조청으로 만든 꿀떡 만드는법",JA,
            "찹쌀과 조청으로 집에서도 쉽고 빠르게드실 수 있는 찹쌀 꿀떡을 만들어봤습니다그럼 저희 영상을 봐주시고 채널을 들려주셔서 감사합니다",
            4.2,"120","중급",null, URL("https://recipe1.ezmember.co.kr/cache/recipe/2022/02/17/1f40ef46386de280a5d80601d0d39ae01.jpg")
        ))
        recipeArray.add(RecipeItem("2","단짠단짠의 대패덮밥",JA,
            "뜨끈한 밥에 대패삼겹살 한점!집밥백선생 강추레시피! 간단하고 빠르게 만드는 별미메뉴!입맛없을때 만들먹으면 밥두공기도 거뜬해요.",
            4.6,"30","초급",null, URL("https://recipe1.ezmember.co.kr/cache/recipe/2017/10/22/3211f299a02729bc2d05649ceec734771.jpg")
        ))*/




        setContentView(binding.root)


    }
    fun updateUI(itemData:RefItem){
        Log.d("ResponsenUI",itemData.toString())
        binding.refDetailItemname.text = itemData.itemname
        if(itemData.itemexp!=null) {
            year = itemData.itemexp!!.year
            month = itemData.itemexp!!.month
            date = itemData.itemexp!!.date
            binding.refDetailEXP.text = year.toString() + "." + (month+1).toString() + "." + date.toString()
            var diffSec = (itemData.itemexp!!.time.minus(Date(todayyear, todaymonth, todaydate).time))
            var diffDate = diffSec / (24 * 60 * 60 * 1000)
            if(diffDate <= 3) {
                binding.refDetailEXPWarning.setVisibility(View.VISIBLE)
            }
            else {
                binding.refDetailEXPWarning.setVisibility(View.INVISIBLE)
            }
        }
        binding.refDetailItemAmount.text = itemData.itemamount.toString()
        binding.refDetailUnit.text = itemData.itemunit.toString()
        binding.refDetailTag.text = itemData.itemtag.toString()
        recipeArray.clear()

        updateRecipeItem()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.getItemId()) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the options menu from XML
        val inflater = menuInflater
        inflater.inflate(R.menu.refitemdetail_menu, menu)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        menu.findItem(R.id.refitemdetail_deletebutton).setOnMenuItemClickListener(MenuItem.OnMenuItemClickListener {
            when(it.itemId) {
                R.id.refitemdetail_deletebutton -> {
                    //여기다 db삭제 코드 만들어 주세요
                    val sObject = JSONObject() //배열 내에 들어갈 json
                    sObject.put("user_id", AWSMobileClient.getInstance().username)
                    sObject.put("item_id", refItem.itemid.toString())



                    //Log.d("Response sObjects sObject sObject sObject : ",sObject.toString())

                    delete_ref_item(sObject)

                    true
                }
                else->{
                    false
                }
            }
        })
        return super.onCreateOptionsMenu(menu)
    }

    fun update_ref_item(job : JSONObject){
        Thread(Runnable{
            //handler.post{
            //try {
            AWSMobileClient.getInstance()

            val url: URL = URL("https://b62cvdj81b.execute-api.ap-northeast-2.amazonaws.com/ref-api-test/ref/update")
            var conn: HttpURLConnection =url.openConnection() as HttpURLConnection
            conn.setUseCaches(false)
            conn.setRequestMethod("POST")
            //conn.setRequestProperty("Cache-Control", "no-cache");
            conn.setRequestProperty("Content-Type", "application/json")
            conn.setRequestProperty("Connection","keep-alive")
            //conn.setRequestProperty("x-api-key","xL0xZytlwwcGVllGMWN34yrPsaiEbBa5undCLf50")
            conn.setRequestProperty("Accept", "application/json")
            conn.setDoOutput(true)
            conn.setDoInput(true)
            //conn.connect()


            var requestBody = job.toString()


            Log.d("Response1 = ",requestBody)
            val wr = DataOutputStream(conn.getOutputStream())
            wr.writeBytes(requestBody)
            wr.flush()
            wr.close()

            var responseCode = conn.getResponseCode()
            Log.d("Response : responseCode",responseCode.toString())
            val br: BufferedReader
            if (responseCode == 200) {
                br = BufferedReader(InputStreamReader(conn.getInputStream()))

                //var re = br.readLine()
                //Log.d("Response : br.readLine(1) = ",re)
                //var re1 = br.readLine()
                //Log.d("Response : br.readLine(2) = ",re1)
                //var re2 = br.readLine()
                //Log.d("Response : br.readLine(3) = ",re2)
                //var re3 = br.readLine()
                //Log.d("Response : br.readLine() = ",re3)
                //var re4 = br.readLine()
                //Log.d("Response : br.readLine() = ",re4)

                //var ree = JSONObject(re)
                //Log.d("Response : JSONObject(br.readLine()) = ",ree.toString())
                //var reee = ree.toString()
                //Log.d("Response : resultJson = ",reee)
                Log.d("Response","Success Success Success Success Success Success")
            } else {
                br = BufferedReader(InputStreamReader(conn.getErrorStream()))
                Log.d("Response","fail")
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

    fun delete_ref_item(job : JSONObject){
        val handler = Handler()
        Thread(Runnable{
            //handler.post{
            //try {
            AWSMobileClient.getInstance()

            val url: URL = URL("https://b62cvdj81b.execute-api.ap-northeast-2.amazonaws.com/ref-api-test/ref/delete")
            var conn: HttpURLConnection =url.openConnection() as HttpURLConnection
            conn.setUseCaches(false)
            conn.setRequestMethod("POST")
            //conn.setRequestProperty("Cache-Control", "no-cache");
            conn.setRequestProperty("Content-Type", "application/json")
            conn.setRequestProperty("Connection","keep-alive")
            //conn.setRequestProperty("x-api-key","xL0xZytlwwcGVllGMWN34yrPsaiEbBa5undCLf50")
            conn.setRequestProperty("Accept", "application/json")
            conn.setDoOutput(true)
            conn.setDoInput(true)
            //conn.connect()


            var requestBody = job.toString()


            Log.d("Response1 = ",requestBody)
            val wr = DataOutputStream(conn.getOutputStream())
            wr.writeBytes(requestBody)
            wr.flush()
            wr.close()

            var responseCode = conn.getResponseCode()
            Log.d("Response : responseCode",responseCode.toString())
            val br: BufferedReader
            if (responseCode == 200) {
                br = BufferedReader(InputStreamReader(conn.getInputStream()))

                //var re = br.readLine()
                //Log.d("Response : br.readLine(1) = ",re)
                //var re1 = br.readLine()
                //Log.d("Response : br.readLine(2) = ",re1)
                //var re2 = br.readLine()
                //Log.d("Response : br.readLine(3) = ",re2)
                //var re3 = br.readLine()
                //Log.d("Response : br.readLine() = ",re3)
                //var re4 = br.readLine()
                //Log.d("Response : br.readLine() = ",re4)

                //var ree = JSONObject(re)
                //Log.d("Response : JSONObject(br.readLine()) = ",ree.toString())
                //var reee = ree.toString()
                //Log.d("Response : resultJson = ",reee)
                Log.d("Response","Success Success Success Success Success Success")
            } else {
                br = BufferedReader(InputStreamReader(conn.getErrorStream()))
                Log.d("Response","fail")
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

            handler.post{
                Toast.makeText(this, "삭제되었습니다", Toast.LENGTH_LONG).show()
                finish()
            }




        }).start()
    }

    fun get_recipe_item(job : JSONObject) : ArrayList<RecipeItem>{
        //Thread(Runnable{
        //handler.post{
        //try {
        AWSMobileClient.getInstance()
        val recipeTestList = ArrayList<RecipeItem>()

        val url: URL = URL("https://b62cvdj81b.execute-api.ap-northeast-2.amazonaws.com/ref-api-test/ref/item_get_recipe")
        var conn: HttpURLConnection =url.openConnection() as HttpURLConnection
        conn.setUseCaches(false)
        conn.setRequestMethod("POST")
        //conn.setRequestProperty("Cache-Control", "no-cache");
        conn.setRequestProperty("Content-Type", "application/json")
        conn.setRequestProperty("Connection","keep-alive")
        //conn.setRequestProperty("x-api-key","xL0xZytlwwcGVllGMWN34yrPsaiEbBa5undCLf50")
        conn.setRequestProperty("Accept", "application/json")
        conn.setDoOutput(true)
        conn.setDoInput(true)
        //conn.connect()


        var requestBody = job.toString()


        Log.d("Response1 = ",requestBody)
        val wr = DataOutputStream(conn.getOutputStream())
        wr.writeBytes(requestBody)
        wr.flush()
        wr.close()

        val streamReader = InputStreamReader(conn.inputStream)
        val buffered = BufferedReader(streamReader)

        val content = StringBuilder()
        while(true) {
            val line = buffered.readLine() ?: break
            content.append(line)
        }

        val data =content.toString()
        val jsonArr = JSONArray(data)
        //Log.d("Response : jsonArr",jsonArr.getJSONObject(0).getJSONArray("recipe").toString())
        //Log.d("Response : jsonlength",jsonArr.length().toString())
        val i = 0
        for (i in 0 until jsonArr.length()) {
            val jsonObj = jsonArr.getJSONObject(i)
            val recipeStep = ArrayList<RecipeProcess>()
            val recipeIngre = ArrayList<IngredientsInfo>()
            for(j in 0 until jsonArr.getJSONObject(i).getJSONArray("recipe").length()){
                recipeStep.add(RecipeProcess(jsonArr.getJSONObject(i).getJSONArray("recipe").getJSONObject(j).getString("txt"),URL(jsonArr.getJSONObject(i).getJSONArray("recipe").getJSONObject(j).getString("img"))))
            }
            for(j in 0 until jsonArr.getJSONObject(i).getJSONArray("ingre").length()){
                recipeIngre.add(IngredientsInfo(jsonArr.getJSONObject(i).getJSONArray("ingre").getJSONObject(j).getString("ingre_name"),jsonArr.getJSONObject(i).getJSONArray("ingre").getJSONObject(j).getString("ingre_count").toDoubleOrNull(),jsonArr.getJSONObject(i).getJSONArray("ingre").getJSONObject(j).getString("ingre_unit")))
            }
            Log.d("Response : recipeStep", "RecipeStep"+recipeStep.toString())
            Log.d("Response : jsonObj",jsonObj.toString())
            recipeTestList.add(
                RecipeItem(
                    jsonObj.getString("id"),
                    jsonObj.getString("name"),
                    recipeIngre,
                    //jsonObj.getString("ingre"),
                    jsonObj.getString("summary"),
                    5.0,
                    jsonObj.getString("cooktime"),
                    jsonObj.getString("difficulty"),
                    "만개의 레시피",
                    URL(jsonObj.getString("img")),
                    recipeStep

                ))
            Log.d("Response : ingre______",JSONArray(jsonObj.getString("ingre")).toString())
        }

        // 스트림과 커넥션 해제
        buffered.close()
        conn.disconnect()






        //}).start()
        return recipeTestList
    }

    fun updateRecipeItem(){
        val handler = Handler()

        Thread(Runnable{

            val rObject = JSONObject()

            rObject.put("item_tag", URLEncoder.encode(refItem.itemtag.toString(), "UTF-8"))

            val recipe_list = get_recipe_item(rObject)

            Log.d("Response : recipelist ------------------- = ",recipe_list.toString())


            for(x in recipe_list) {
                recipeArray.add(RecipeItem(
                    x.recipeId, x.recipeName, x.recipeIngredients, x.recipeSummary, x.recipeRating, x.recipeTime, x.recipeDifficulty, x.recipeWriter, x.imgURL,x.recipeStep)
                )
            }
            handler.post{
                if(recipeArray.size==0) {
                    binding.refDetailItemNoRecipeText.visibility = View.VISIBLE

                }
                else{
                    adapter = RecipeItemAdapter(recipeArray)
                    binding.refDetailRecipeField.adapter = adapter
                }
            }

        }).start()

    }

}