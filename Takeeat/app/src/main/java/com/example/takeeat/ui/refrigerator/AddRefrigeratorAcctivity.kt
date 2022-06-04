package com.example.takeeat.ui.refrigerator

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.AnimationDrawable
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.MenuItem
import android.widget.ImageView
import android.widget.ScrollView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDialog
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.amazonaws.mobile.client.AWSMobileClient
import com.example.takeeat.R
import com.example.takeeat.databinding.ActivityAddrefrigeratorBinding
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.BufferedReader
import java.io.DataOutputStream
import java.io.InputStreamReader
import java.lang.Thread.sleep
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
            viewmodel.addData(RefItem(null, null, null, null, null, null))
            Log.d("Response", "inMain"+viewmodel.liveData.value.toString())
            adapter.notifyItemInserted(viewmodel.getCount())
            scrolldown()
        }
        binding.addrefApplyButton.setOnClickListener {
            //DB추가 여기다 붙여주세요
            //값은 viewmodel.liveData.value(ArrayList<RefItem>타입)을 for문돌려서 추가하면 될거 같아요
            progressON(this)
            val jArray = JSONArray()//배열
            // viewmodel -> json 변환

            try {

                for (i in 0 until viewmodel.getCount()) {
                    val sObject = JSONObject() //배열 내에 들어갈 json
                    val en_name = URLEncoder.encode(viewmodel.liveData.value!![i].itemname, "UTF-8").replace("+", " ")
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
                        sObject.put("item_exdate", viewmodel.liveData.value!![i].itemexp!!.year.toString() + "-" + (viewmodel.liveData.value!![i].itemexp!!.month+1).toString() + "-" +viewmodel.liveData.value!![i].itemexp!!.date.toString())
                    }
                    sObject.put("item_amount", viewmodel.liveData.value!![i].itemamount.toString())
                    sObject.put("item_unit", en_unit)
                    sObject.put("user_id", AWSMobileClient.getInstance().username)
                    jArray.put(sObject)
                    input_ref_item(sObject)
                }
                Log.d("Response", jArray.toString())
            } catch (e: JSONException) {
                e.printStackTrace()
            }


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
        val handler = Handler()
        Thread(Runnable{

            val url: URL = URL("https://b62cvdj81b.execute-api.ap-northeast-2.amazonaws.com/ref-api-test/ref")
            var conn: HttpURLConnection =url.openConnection() as HttpURLConnection
            conn.setUseCaches(false)
            conn.setRequestMethod("POST")
            conn.setRequestProperty("Content-Type", "application/json")
            conn.setRequestProperty("Connection","keep-alive")
            conn.setRequestProperty("Accept", "application/json")
            conn.setDoOutput(true)
            conn.setDoInput(true)

            var requestBody = job.toString()
            Log.d("Responseeee", job.toString())

            val wr = DataOutputStream(conn.getOutputStream())
            wr.writeBytes(requestBody)
            wr.flush()
            wr.close()

            Log.d("Responseeee : code ", conn.responseCode.toString())

            conn.disconnect()



            handler.post{
                sleep(500)
                progressOFF()
                finish()
            }





        }).start()
    }

    fun scrolldown(){
        val scroll  = binding.addrefScrollView
        scroll.post { scroll.fullScroll(ScrollView.FOCUS_DOWN) }
    }
    lateinit var progressDialog : AppCompatDialog
    fun progressON(context: Context){
        if(context == null){
            return
        }
        progressDialog = AppCompatDialog(context)
        progressDialog.setCancelable(false)
        progressDialog.getWindow()!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
        progressDialog.setContentView(R.layout.dialog_loading);
        progressDialog.show();
        val loadingFrame : ImageView? = progressDialog.findViewById(R.id.loadingImage)
        if(loadingFrame != null) {
            val frameAnimation = loadingFrame.background as AnimationDrawable
            loadingFrame.post(Runnable { frameAnimation.start()})

        }

    }
    fun progressOFF(){
        if(progressDialog != null && progressDialog.isShowing){
            progressDialog.dismiss()
        }
    }

}