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