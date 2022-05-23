package com.example.takeeat

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.takeeat.MainActivity.Companion.createNotificationChannel
import com.example.takeeat.MainActivity.Companion.startAlarm

class BootReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Intent.ACTION_BOOT_COMPLETED) {
            startAlarm(context)
            createNotificationChannel(context)
        }
    }
}