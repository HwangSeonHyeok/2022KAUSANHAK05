package com.example.takeeat.ui.menu

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MenuViewModel: ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is menu Fragment"
    }
    val text: LiveData<String> = _text
}