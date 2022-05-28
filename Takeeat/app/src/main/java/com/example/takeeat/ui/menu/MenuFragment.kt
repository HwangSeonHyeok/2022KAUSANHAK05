package com.example.takeeat.ui.menu

import android.app.AlertDialog
import android.app.Dialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceDataStore
import android.text.format.DateFormat.is24HourFormat
import android.util.Log
import android.view.*
import android.widget.*
import androidx.appcompat.widget.SwitchCompat
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.preference.PreferenceManager
import com.amazonaws.mobile.client.AWSMobileClient
import com.amazonaws.mobile.client.Callback
import com.amazonaws.mobile.client.UserStateDetails
import com.example.takeeat.*
import com.example.takeeat.databinding.FragmentMenuBinding
import com.example.takeeat.ui.refrigerator.RefItemAppDatabase
import com.example.takeeat.ui.refrigerator.RefItemAppDatabase_Impl
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.text.DateFormat
import java.util.*
import kotlin.math.log


class MenuFragment: Fragment() {

    private var _binding: FragmentMenuBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    //val sharedPref = activity?.getSharedPreferences("com.example.takeeat.PREFERENCE_FILE_KEY",Context.MODE_PRIVATE)
    lateinit var version_number: TextView
    lateinit var notification_toggle_switch: SwitchCompat
    lateinit var notification_time_set_button: ConstraintLayout
    lateinit var icon_source_button: ConstraintLayout
    lateinit var logout_button: ConstraintLayout


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMenuBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val sharedPref = PreferenceManager.getDefaultSharedPreferences(context?.applicationContext)

        version_number = binding.versionNumber
        notification_toggle_switch = binding.notificationToggleSwitch
        notification_time_set_button = binding.notificationTimeSettingsButton
        icon_source_button = binding.iconSourceButton
        logout_button = binding.logoutButton

        /*
        val logoutbutton : Button = binding.logoutbutton
        logoutbutton.setOnClickListener {
            //여기다 auth값을 false로 하거나 계정관련 변수를 초기화 해주세요
            AWSMobileClient.getInstance().initialize(
                logoutbutton.context,
                object : Callback<UserStateDetails?> {
                    override fun onResult(userStateDetails: UserStateDetails?) {
                        // 로그아웃 후 로그인 창으로 이동
                        AWSMobileClient.getInstance().signOut()
                        val i = Intent(logoutbutton.context, AuthActivity::class.java)
                        startActivity(i)
                        activity!!.finish()
                    }

                    override fun onError(e: Exception) {}
                })

        }*/
        val versionName = BuildConfig.VERSION_NAME
        version_number.text = "v$versionName"


        val notificationToggle = sharedPref?.getBoolean(getString(R.string.notification_toggle),true)
        notification_toggle_switch.isChecked = notificationToggle == true
        Log.d("menu: ",notificationToggle.toString())


        notification_toggle_switch.setOnCheckedChangeListener { _, isChecked ->
            Log.d("menu: ","switch toggled")
            val editor = sharedPref.edit()
            if(isChecked){
                context?.let { MainActivity.cancelAlarm(it)}
                editor.putBoolean(getString(R.string.notification_toggle),true)
                editor.apply()
                context?.let { MainActivity.startAlarm(it) }
            }
            else{
                editor.putBoolean(getString(R.string.notification_toggle),false)
                editor.apply()
                context?.let { MainActivity.cancelAlarm(it) }
            }

        }

        notification_time_set_button.setOnClickListener {
            val timePickerDialogListener: TimePickerDialog.OnTimeSetListener =
                object : TimePickerDialog.OnTimeSetListener {
                    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
                        Log.d("menu: ", "$hourOfDay:$minute")
                        val editor = sharedPref.edit()
                        editor.putInt(getString(R.string.notification_hour),hourOfDay)
                        editor.putInt(getString(R.string.notification_minute),minute)
                        editor.apply()
                        context?.let { MainActivity.cancelAlarm(it)}
                        context?.let { MainActivity.startAlarm(it) }
                        Toast.makeText(context, "알림은 설정 시간 후 15분 이내에 울립니다.", Toast.LENGTH_SHORT).show()
                    }
                }
            val alarmHour = sharedPref.getInt(context?.getString(R.string.notification_hour),10)
            val alarmMinute = sharedPref.getInt(context?.getString(R.string.notification_minute),0)
            val timePicker = TimePickerDialog(context, timePickerDialogListener, alarmHour, alarmMinute, false)
            timePicker.show()
        }

        icon_source_button.setOnClickListener {
            val alertDialog: AlertDialog? = activity?.let {
                val builder = AlertDialog.Builder(it)
                val tags = context?.resources?.getStringArray(R.array.IconSourceArray)
                builder.apply {
                    setTitle("아이콘 출처")
                    setItems(tags,DialogInterface.OnClickListener { dialog, which ->
                        // The 'which' argument contains the index position
                        // of the selected item
                    })
                }
                builder.create()
            }
            if (alertDialog != null) {
                alertDialog.show()
            }
        }


        logout_button.setOnClickListener {
            val alertDialog: AlertDialog? = activity?.let {
                val builder = AlertDialog.Builder(it)
                builder.apply {
                    setNegativeButton("예",
                        DialogInterface.OnClickListener { dialog, id ->
                            //여기다 auth값을 false로 하거나 계정관련 변수를 초기화 해주세요
                            AWSMobileClient.getInstance().initialize(
                                context,
                                object : Callback<UserStateDetails?> {
                                    override fun onResult(userStateDetails: UserStateDetails?) {
                                        // 로그아웃 후 로그인 창으로 이동
                                        AWSMobileClient.getInstance().signOut()
                                        val i = Intent(context, AuthActivity::class.java)
                                        startActivity(i)
                                        activity!!.finish()
                                    }

                                    override fun onError(e: Exception) {}
                                })
                        })
                    setPositiveButton("아니오",
                        DialogInterface.OnClickListener { dialog, id ->
                        })
                    setTitle("로그아웃할까요?")
                }
                builder.create()
            }
            if (alertDialog != null) {
                alertDialog.show()
            }
        }



        setHasOptionsMenu(true)

        return root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.removeItem(R.id.app_bar_search_refrigerator)
        menu.removeItem(R.id.app_bar_search_recipe)
        menu.removeItem(R.id.app_bar_search_myrecipe)
        menu.removeItem(R.id.cart_button)
        menu.removeItem(R.id.notification_button)
        inflater.inflate(R.menu.search_menu, menu)

        val searchButtonRefrigerator = menu.findItem(R.id.app_bar_search_refrigerator)
        val searchButtonRecipe = menu.findItem(R.id.app_bar_search_recipe)
        val searchButtonMyrecipe = menu.findItem(R.id.app_bar_search_myrecipe)
        searchButtonRefrigerator.isVisible = false
        searchButtonRecipe.isVisible = false
        searchButtonMyrecipe.isVisible = false

        menu.findItem(R.id.cart_button).setOnMenuItemClickListener(MenuItem.OnMenuItemClickListener {

            val shoppingintent: Intent = Intent(context, ShoppingListActivity::class.java)
            startActivity(shoppingintent)
            true
        })

        menu.findItem(R.id.notification_button).setOnMenuItemClickListener(MenuItem.OnMenuItemClickListener {

            val notificationintent: Intent = Intent(context, NotificationActivity::class.java)
            startActivity(notificationintent)
            true
        })

        return super.onCreateOptionsMenu(menu,inflater)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}

