package com.example.takeeat

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.room.*
import com.example.takeeat.databinding.ActivityShoppinglistBinding
import com.example.takeeat.ui.refrigerator.RefItem


class ShoppingListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityShoppinglistBinding

    lateinit var viewmodel : ShoppingListViewModel
    lateinit var adapter : ShoppingListAdapter
    lateinit var db : AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShoppinglistBinding.inflate(layoutInflater)
        viewmodel = ViewModelProviders.of(this).get(ShoppingListViewModel::class.java)
        db = Room.databaseBuilder(this,AppDatabase::class.java,"shoppinglistitem").allowMainThreadQueries().build()
        for(dbitem in db.itemDao().getAll())
            viewmodel.addData (dbitem)
        setContentView(binding.root)
        val dataObserver: Observer<ArrayList<ShoppingListItem>> =
            Observer {livedata ->
                Log.d("Response","here?")
                adapter = ShoppingListAdapter(viewmodel.liveData)
                binding.shoppingListRecyclerView.adapter = adapter
                for(item in livedata)
                    db.itemDao().updateItem(item)


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
                db.itemDao().delete(viewmodel.shoppingListItemData.get(viewHolder.layoutPosition))
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
                    val item = ShoppingListItem(0,null,1)
                    viewmodel.addData(item)
                    db.itemDao().insertItem(item)
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
                if(viewmodel.liveData.value!=null) {
                    for (item in viewmodel.liveData.value!!)
                        db.itemDao().updateItem(item)
                }
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        if(viewmodel.liveData.value!=null) {
            Log.d("Response","update")
            for (item in viewmodel.liveData.value!!)
                db.itemDao().updateItem(item)
        }
    }




}

@Entity(tableName = "shoppinglistitem")
public data class ShoppingListItem (
    @PrimaryKey(autoGenerate = true) val id : Int,
    @ColumnInfo var itemname : String?,
    @ColumnInfo var itemamount: Int
)
@Dao
interface  ItemDao{
    @Query("SELECT * FROM shoppinglistitem")
    fun getAll(): List<ShoppingListItem>
    /*@Query("SELECT * FROM shoppinglistitem WHERE itemName")
    fun findByItemName()*/
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertItem(vararg shoppinglistitem : ShoppingListItem)
    @Delete
    fun delete(shoppingListItem: ShoppingListItem)
    @Query("DELETE FROM shoppinglistitem")
    fun deleteAll()
    @Update
    fun updateItem(vararg shoppinglistitem : ShoppingListItem)

}
@Database(entities = [ShoppingListItem::class], version = 1)
abstract class AppDatabase : RoomDatabase(){
    abstract fun itemDao() : ItemDao
}
