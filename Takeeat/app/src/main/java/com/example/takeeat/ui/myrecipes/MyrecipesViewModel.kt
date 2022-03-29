package com.example.takeeat.ui.myrecipes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MyrecipesViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is my recipes Fragment"
    }
    val text: LiveData<String> = _text
}