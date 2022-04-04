package com.example.takeeat.ui.refrigerator

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.takeeat.databinding.ActivityAddrefrigeratorBinding

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
        val dataObserver: Observer<ArrayList<RefItem>> =
            Observer {livedata ->
                data.value = livedata
                var newAdapter = AddRefrigeratorAdapter(data)
                binding.addrefRecyclerView.adapter = newAdapter
            }
        viewmodel.liveData.observe(this, dataObserver)
    }
    public fun test(){

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