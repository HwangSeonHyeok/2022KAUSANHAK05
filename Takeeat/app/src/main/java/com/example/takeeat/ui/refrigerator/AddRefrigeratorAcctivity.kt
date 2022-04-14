package com.example.takeeat.ui.refrigerator

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.takeeat.databinding.ActivityAddrefrigeratorBinding
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.BufferedReader
import java.io.DataOutputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder

class AddRefrigeratorActivity :AppCompatActivity() {
    lateinit var binding : ActivityAddrefrigeratorBinding
    lateinit var viewmodel : AddRefrigeratorViewModel
    lateinit var adapter : AddRefrigeratorAdapter
    var data = MutableLiveData<ArrayList<RefItem>>()
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        binding = ActivityAddrefrigeratorBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewmodel = ViewModelProviders.of(this).get(AddRefrigeratorViewModel::class.java)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        val intentExtra = intent.getSerializableExtra("OCR_RESULT") as ArrayList<RefItem>
        if(viewmodel.getCount() == 0) {
            for (resultItem in intentExtra) {
                viewmodel.addData(resultItem)
            }
        }

        binding.addrefAddButton.setOnClickListener {
            viewmodel.addData(RefItem(null, null, null, null, null))
            Log.d("Response", "inMain"+viewmodel.liveData.value.toString())
        }
        binding.addrefApplyButton.setOnClickListener {
            //DB추가 여기다 붙여주세요
            //값은 viewmodel.liveData.value(ArrayList<RefItem>타입)을 for문돌려서 추가하면 될거 같아요
            val jArray = JSONArray()//배열
            // viewmodel -> json 변환
            try {
                Log.d("Response",viewmodel.liveData.value.toString())

                for (i in 0 until viewmodel.getCount()) {
                    val sObject = JSONObject() //배열 내에 들어갈 json
                    val en_name = URLEncoder.encode(viewmodel.liveData.value!![i].itemname, "UTF-8")
                    if(viewmodel.liveData.value!![i].itemtag!=null){
                        val en_tag = URLEncoder.encode(viewmodel.liveData.value!![i].itemtag, "UTF-8")
                        sObject.put("item_tag", en_tag)
                    }
                    val en_unit = URLEncoder.encode(viewmodel.liveData.value!![i].itemunit, "UTF-8")
                    sObject.put("item_name", en_name)

                    if(viewmodel.liveData.value!![i].itemtag==null) {
                        sObject.put("item_tag", "NULL")
                    }

                    if(viewmodel.liveData.value!![i].itemexp==null){
                        sObject.put("item_exdate", "NULL")
                    }else{
                        sObject.put("item_exdate", viewmodel.liveData.value!![i].itemexp)
                    }
                    sObject.put("item_amount", viewmodel.liveData.value!![i].itemamount)
                    sObject.put("item_unit", en_unit)
                    jArray.put(sObject)
                    input_ref_item(sObject)
                }
                Log.d("Response", jArray.toString())
            } catch (e: JSONException) {
                e.printStackTrace()
            }

            finish()
        }
        val simplecallback = object: ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false;
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                adapter.notifyItemRemoved(viewHolder.layoutPosition)
                viewmodel.deleteData(viewHolder.layoutPosition)
            }
        }
        val itemTouchHelper = ItemTouchHelper(simplecallback)
        itemTouchHelper.attachToRecyclerView(binding.addrefRecyclerView)
        val dataObserver: Observer<ArrayList<RefItem>> =
            Observer {livedata ->
                data.value = livedata
                adapter = AddRefrigeratorAdapter(data)
                binding.addrefRecyclerView.adapter = adapter
                Log.d("Response", livedata.toString())
            }
        viewmodel.liveData.observe(this, dataObserver)
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

    fun input_ref_item(job : JSONObject){
        Thread(Runnable{
            //handler.post{
            //try {

            val url: URL = URL("https://b62cvdj81b.execute-api.ap-northeast-2.amazonaws.com/ref-api-test/ref")
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

            /*
            var job = JSONObject()

            job.put("item_name", "안드테스트")
            job.put("item_amount", "2203")
            job.put("item_exdate", "22-07-05")
            job.put("item_unit","g")

             */


            var requestBody = job.toString()


            Log.d("Response : requestBody = ",requestBody)
            val wr = DataOutputStream(conn.getOutputStream())
            wr.writeBytes(requestBody)
            wr.flush()
            wr.close()

            var responseCode = conn.getResponseCode()
            Log.d("Response : responseCode",responseCode.toString())
            val br: BufferedReader
            if (responseCode == 200) {
                br = BufferedReader(InputStreamReader(conn.getInputStream(), "euc-kr"))

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
                Log.d("Response","Success")
            } else {
                br = BufferedReader(InputStreamReader(conn.getErrorStream(), "euc-kr"))
                Log.d("Response","fail")
            }

            /*
            var re = br.readLine()
            var ree = JSONObject(re)
            var reee = ree.toString()
            Log.d("Response : resultJson = ",reee)
             */

            var resultJson= JSONObject(br.readLine())
            //var rrr = br.readLine()
            Log.d("Response : resultJson = ",resultJson.toString())

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