package com.example.takeeat.ui.myrecipe

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MyRecpieViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is myrecipe Fragment"
    }
    val text: LiveData<String> = _text
}