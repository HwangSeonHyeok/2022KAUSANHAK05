package com.example.takeeat

import android.app.Notification
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.room.*
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.takeeat.databinding.ActivityNotificationBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class NotificationActivity : AppCompatActivity() {

    private lateinit var binding : ActivityNotificationBinding
    lateinit var viewmodel : NotificationViewModel
    lateinit var adapter: NotificationAdapter
    lateinit var db : NotifAppDatabase


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNotificationBinding.inflate(layoutInflater)
        viewmodel = ViewModelProviders.of(this).get(NotificationViewModel::class.java)
        db = NotifAppDatabase.getDatabase(this)
        adapter = NotificationAdapter(viewmodel.liveData)
        //dbTest()

        setContentView(binding.root)
        if(viewmodel.getCount()==0) {
            GlobalScope.launch(Dispatchers.IO) {
                for (dbitem in db.notifDao().getAll())
                    viewmodel.addData(dbitem)
            }
        }
        val dataObserver: Observer<ArrayList<NotificationItem>> =
            Observer {livedata ->
                Log.d("Response","here?")
                binding.shoppingListRecyclerView.adapter = adapter
                GlobalScope.launch(Dispatchers.IO) {
                    for (item in livedata)
                        db.notifDao().updateItem(item)
                }
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
                GlobalScope.launch(Dispatchers.IO) {
                    db.notifDao().delete(viewmodel.notificationItemData.get(viewHolder.layoutPosition))
                    viewmodel.deleteData(viewHolder.layoutPosition)
                }
            }
        }
        val itemTouchHelper = ItemTouchHelper(simplecallback)
        itemTouchHelper.attachToRecyclerView(binding.shoppingListRecyclerView)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.notification_menu, menu)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.getItemId()) {
            android.R.id.home -> {
                finish()
                return true
            }
            R.id.remove_notifications_button -> {
                adapter.notifyDataSetChanged()
                GlobalScope.launch(Dispatchers.IO) {
                    db.notifDao().deleteAll()
                    viewmodel.deleteAll()
                }
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    //DB 테스트용
    //fun dbTest(){
    //    db.notifDao().insertItem(NotificationItem("테스트1"))
    //    db.notifDao().insertItem(NotificationItem("테스트2"))
    //    db.notifDao().insertItem(NotificationItem("테스트3"))
    //}
}


@Entity(tableName = "notificationlist")
public data class NotificationItem (
    @PrimaryKey val id : Long,
    @ColumnInfo var refItemName: String
)
@Dao
interface  NotifDao{
    @Query("SELECT * FROM notificationlist")
    fun getAll(): List<NotificationItem>
    /*@Query("SELECT * FROM notificationitem WHERE itemName")
    fun findByItemName()*/
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertItem(vararg notificationItem: NotificationItem)
    @Delete
    fun delete(notificationItem: NotificationItem)
    @Query("DELETE FROM notificationlist")
    fun deleteAll()
    @Update
    fun updateItem(vararg notificationItem: NotificationItem)

}
@Database(entities = [NotificationItem::class], version = 2)
abstract class NotifAppDatabase : RoomDatabase(){
    abstract fun notifDao() : NotifDao

    companion object{
        @Volatile
        private var INSTANCE: NotifAppDatabase? = null

        fun getDatabase(context: Context) : NotifAppDatabase {
            val tempInstance = INSTANCE
            if(tempInstance != null) return tempInstance
            synchronized(this){
                val instance = Room.databaseBuilder(context.applicationContext,NotifAppDatabase::class.java,"notificationDB").build()
                INSTANCE = instance
                return instance
            }
        }
    }
}

/*private val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("ALTER TABLE notificationlist ADD COLUMN id INTEGER")
        database.execSQL("ALTER TABLE notificationlist ADD COLUMN refitemname TEXT")
    }
}*/