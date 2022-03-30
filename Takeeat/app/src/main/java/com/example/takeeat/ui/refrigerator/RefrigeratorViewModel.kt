package com.example.takeeat.ui.refrigerator

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class RefrigeratorViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is Refrigerator Fragment"
    }
    val text: LiveData<String> = _text
}