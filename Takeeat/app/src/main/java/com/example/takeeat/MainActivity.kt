package com.example.takeeat

import android.app.*
import android.content.Context
import android.os.Bundle
import android.text.TextWatcher
import android.view.Menu
import android.widget.SearchView
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.takeeat.databinding.ActivityMainBinding
import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobile.client.Callback;
import com.amazonaws.mobile.client.UserStateDetails;
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.AnimationDrawable
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.preference.PreferenceManager
import android.util.Log
import android.view.MenuItem
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatDialog
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.temporal.ChronoUnit

//testmerge
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        startAlarm(this)
        createNotificationChannel(this)

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        // test
        // test
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_recipe, R.id.navigation_refrigerator, R.id.navigation_myrecipe, R.id.navigation_menu
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the options menu from XML
        val inflater = menuInflater
        inflater.inflate(R.menu.search_menu, menu)
        menu.findItem(R.id.cart_button).setOnMenuItemClickListener(MenuItem.OnMenuItemClickListener {

            when(it.itemId) {
                R.id.cart_button -> {
                    val shoppingintent: Intent = Intent(this, ShoppingListActivity::class.java)
                    startActivity(shoppingintent)
                    true
                }
                R.id.notification_button ->{
                    true

                }
                else->{
                    false
                }
            }
        })

        /*val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        (menu.findItem(R.id.app_bar_search).actionView as SearchView).apply {
            // Assumes current activity is the searchable activity
            setSearchableInfo(searchManager.getSearchableInfo(componentName))
            setIconifiedByDefault(false) // Do not iconify the widget; expand it by default
        }*/
        return super.onCreateOptionsMenu(menu)
    }


    companion object {
        fun startAlarm(context: Context){
            val intent = Intent(context, AlarmReceiver::class.java)
            val pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0)
            val alarm = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            val sharedPref = PreferenceManager.getDefaultSharedPreferences(context.applicationContext)

            val notificationToggle = sharedPref?.getBoolean(context.getString(R.string.notification_toggle),true)
            if(notificationToggle == false){
                Log.d("Alarm disabled","")
                return
            }
            // 알림 시간 설정
            val alarmHour = sharedPref.getInt(context.getString(R.string.notification_hour),10)
            val alarmMinute = sharedPref.getInt(context.getString(R.string.notification_minute),0)

            val alarmTime = LocalTime.of(alarmHour,alarmMinute)
            var now = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES)
            val nowTime = now.toLocalTime()
            // if same time, schedule for next day as well
            // if today's time had passed, schedule for next day
            if (nowTime == alarmTime || nowTime.isAfter(alarmTime)) {
                now = now.plusDays(1)
            }
            now = now.withHour(alarmTime.hour).withMinute(alarmTime.minute) // .withSecond(alarmTime.second).withNano(alarmTime.nano)

            // alarm use UTC/GMT time
            val utc = now.atZone(ZoneId.systemDefault()).withZoneSameInstant(ZoneOffset.UTC).toLocalDateTime()
            val startMillis = utc.atZone(ZoneOffset.UTC)!!.toInstant()!!.toEpochMilli()

            Log.d("Alarm will trigger in: ", "${(startMillis-System.currentTimeMillis())/1000}s")

            // AlarmManagerCompat.setExact(alarm, AlarmManager.RTC_WAKEUP, startMillis, pendingIntent!!)
            if (Build.VERSION.SDK_INT >= 19) {
                // alarm.setExact(AlarmManager.RTC_WAKEUP, startMillis, pendingIntent)

                // effort to save battery, allow deviation of 15 minutes
                val windowMillis = 15L * 60L * 1_000L
                alarm.setWindow(AlarmManager.RTC_WAKEUP, startMillis, windowMillis, pendingIntent)
            }
            else {
                alarm.set(AlarmManager.RTC_WAKEUP, startMillis, pendingIntent)
            }
        }

        fun cancelAlarm(context: Context) {
            val intent = Intent(context, AlarmReceiver::class.java)
            val pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0)
            val alarm = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            Log.d("Alarm has been canceled", "")
            alarm.cancel(pendingIntent)
        }

        fun createNotificationChannel(context: Context) {
            // Create the NotificationChannel, but only on API 26+ because
            // the NotificationChannel class is new and not in the support library
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val name = "꺼내먹어요"
                val descriptionText = "꺼내먹어요"
                val importance = NotificationManager.IMPORTANCE_DEFAULT
                val channel = NotificationChannel("꺼내먹어요", name, importance).apply {
                    description = descriptionText
                }
                // Register the channel with the system
                val notificationManager: NotificationManager =
                    context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                notificationManager.createNotificationChannel(channel)
            }
        }


    }




    // 뒤로가기 2번 눌러야 종료
    private val FINISH_INTERVAL_TIME: Long = 2500
    private var backPressedTime: Long = 0
    private var toast: Toast? = null
    override fun onBackPressed() {
        val tempTime = System.currentTimeMillis()
        val intervalTime = tempTime - backPressedTime


        // 뒤로 가기 할 경우 홈 화면으로 이동
        if (0 <= intervalTime && FINISH_INTERVAL_TIME >= intervalTime) {
            super.onBackPressed()
            // 뒤로가기 토스트 종료
            toast!!.cancel()
            finish()
        } else {
            backPressedTime = tempTime
            toast =
                Toast.makeText(applicationContext, "'뒤로'버튼 한번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT)
            toast!!.show()
        }
    }
    lateinit var progressDialog : AppCompatDialog
    fun progressON(){
        if(this == null){
            return
        }
        progressDialog = AppCompatDialog(this)
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