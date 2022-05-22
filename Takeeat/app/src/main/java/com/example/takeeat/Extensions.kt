package com.example.takeeat

import android.app.Activity
import android.util.DisplayMetrics
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect

fun <T> StateFlow<T>.launchWhenStarted(lifecycleOwner: LifecycleOwner, collector: (T) -> Unit) {
    lifecycleOwner.lifecycleScope.launchWhenStarted {
        collect {
            collector(it)
        }
    }
}

fun Activity.getDeviceSize(): List<Int> {
    var deviceWidth = 0
    var deviceHeight = 0
    val outMetrics = DisplayMetrics()
    //defaultDisplay Deprecated로 인한 Version 처리

    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
        val display = this.display
        display?.getRealMetrics(outMetrics)
        deviceHeight = outMetrics.heightPixels
        deviceWidth = outMetrics.widthPixels
    } else {
        @Suppress("DEPRECATION")
        val display = this.windowManager.defaultDisplay
        @Suppress("DEPRECATION")
        display.getMetrics(outMetrics)
        deviceHeight = outMetrics.heightPixels
        deviceWidth = outMetrics.widthPixels
    }
    return listOf(deviceWidth, deviceHeight)
}