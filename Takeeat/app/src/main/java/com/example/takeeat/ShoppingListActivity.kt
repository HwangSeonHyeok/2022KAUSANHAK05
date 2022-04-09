package com.example.takeeat

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.takeeat.databinding.ActivityShoppinglistBinding
import com.example.takeeat.ui.refrigerator.AddRefrigeratorAdapter
import com.example.takeeat.ui.refrigerator.AddRefrigeratorViewModel
import com.example.takeeat.ui.refrigerator.RefItem
import com.google.android.material.bottomnavigation.BottomNavigationView

class ShoppingListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityShoppinglistBinding

    lateinit var viewmodel : ShoppingListViewModel
    lateinit var adapter : ShoppingListAdapter
    var data = MutableLiveData<ArrayList<RefItem>>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityShoppinglistBinding.inflate(layoutInflater)
        //supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        viewmodel = ViewModelProviders.of(this).get(ShoppingListViewModel::class.java)
        setContentView(binding.root)
        val dataObserver: Observer<ArrayList<RefItem>> =
            Observer {livedata ->
                Log.d("Response","here?")
                data.value = livedata
                adapter = ShoppingListAdapter(data)
                binding.shoppingListRecyclerView.adapter = adapter
            }
        viewmodel.liveData.observe(this, dataObserver)


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
        itemTouchHelper.attachToRecyclerView(binding.shoppingListRecyclerView)


    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the options menu from XML
        val inflater = menuInflater
        inflater.inflate(R.menu.shoppinglist_menu, menu)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        menu.findItem(R.id.addShoppingList_button).setOnMenuItemClickListener(MenuItem.OnMenuItemClickListener {
            when(it.itemId) {
                R.id.addShoppingList_button -> {
                    viewmodel.addData(RefItem(null,null,null,1,null))
                    true
                }
                else->{
                    false
                }
            }
        })
        return super.onCreateOptionsMenu(menu)
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