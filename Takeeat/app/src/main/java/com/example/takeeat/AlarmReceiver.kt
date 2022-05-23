package com.example.takeeat

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Handler
import android.os.HandlerThread
import android.os.SystemClock
import android.util.Log
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.takeeat.MainActivity.Companion.startAlarm
import com.example.takeeat.ui.refrigerator.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.temporal.ChronoUnit
import java.util.*

class AlarmReceiver : BroadcastReceiver() {

    lateinit var viewmodel : RefrigeratorViewModel
    lateinit var db : RefItemAppDatabase

    override fun onReceive(context: Context, intent: Intent) {
        try {
            GlobalScope.launch(Dispatchers.IO) {
                var db = RefItemAppDatabase.getDatabase(context)
                var notifdb = NotifAppDatabase.getDatabase(context)
                var calendar = Calendar.getInstance()
                var year = calendar.get(Calendar.YEAR)
                var month = calendar.get(Calendar.MONTH)
                var date = calendar.get(Calendar.DAY_OF_MONTH)

                for (x in db.refdbDao().getAll()) {
                    var diffSec = (x.itemexp!!.minus(Date(year, month, date).time))
                    var diffDate = diffSec / (24 * 60 * 60 * 1000)
                    var notifid = SystemClock.uptimeMillis().toInt()
                    if(diffDate <= 3) {
                        Log.d("alarm : ",x.itemid.toString() + " " + x.itemname )
                        var builder = NotificationCompat.Builder(context, "꺼내먹어요")
                            .setSmallIcon(R.drawable.takeeatappicon)
                            .setContentTitle("꺼내먹어요")
                            .setContentText(x.itemname + "의 유통기한이 얼마 남지 않았습니다!")
                            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                        try {
                            with(NotificationManagerCompat.from(context)) {
                                // notificationId is a unique int for each notification that you must define
                                notify(notifid, builder.build())
                            }

                        }
                        catch (e: Exception) {
                            Log.d("alarm notification:",e.toString())
                        }
                        x.itemname?.let {
                            NotificationItem(x.itemid.toLong(),
                                it
                            )
                        }?.let { notifdb.notifDao().insertItem(it) }
                    }
                }
            }

            Log.d("alarm : ","alarm sounded" )
        }
        catch (e: Exception) {
            Log.d("alarm :",e.toString())
        }
        finally {
            startAlarm(context)
        }
    }


}

