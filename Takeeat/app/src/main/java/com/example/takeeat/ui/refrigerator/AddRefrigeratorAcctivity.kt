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
        for(resultItem in intentExtra){
            viewmodel.addData(resultItem)
        }

        binding.addrefAddButton.setOnClickListener {
            viewmodel.addData(RefItem(null, null, null, null, null))
            Log.d("Response", "inMain"+viewmodel.getCount().toString())
        }
        binding.addrefApplyButton.setOnClickListener {
            //DB추가 여기다 붙여주세요
            //값은 viewmodel.liveData.value(ArrayList<RefItem>타입)을 for문돌려서 추가하면 될거 같아요

            // viewmodel -> json 변환
            try {
                val jArray = JSONArray() //배열
                for (i in 0 until viewmodel.getCount()) {
                    val sObject = JSONObject() //배열 내에 들어갈 json
                    sObject.put("itemname", viewmodel.liveData.value!![i].itemname)
                    sObject.put("itemtag", viewmodel.liveData.value!![i].itemtag)
                    sObject.put("itemexp", viewmodel.liveData.value!![i].itemexp)
                    sObject.put("itemamount", viewmodel.liveData.value!![i].itemamount)
                    sObject.put("itemunit", viewmodel.liveData.value!![i].itemunit)
                    jArray.put(sObject)
                }
                Log.d("JSON Test", jArray.toString())
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
                Log.d("Response", "???????")
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

}