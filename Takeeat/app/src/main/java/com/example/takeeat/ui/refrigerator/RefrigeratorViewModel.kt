package com.example.takeeat.ui.refrigerator

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class RefrigeratorViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is Refrigerator Fragment"
    }
    val text: LiveData<String> = _text
    private val _isOpened = MutableLiveData<Boolean>().apply {
        value = false
    }

}