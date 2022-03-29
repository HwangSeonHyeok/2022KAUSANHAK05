package com.example.takeeat.ui.fridge

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class FridgeViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is fridge Fragment"
    }
    val text: LiveData<String> = _text
}