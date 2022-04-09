package com.example.takeeat.ui.refrigerator

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AddRefrigeratorViewModel : ViewModel()  {
    var liveData : MutableLiveData<ArrayList<RefItem>> = MutableLiveData<ArrayList<RefItem>>()
    var refItemData = ArrayList<RefItem>()
    fun addData(refItem: RefItem){
        refItemData.add(refItem)
        liveData.postValue(refItemData)
    }
    fun deleteData(at : Int){
        refItemData.removeAt(at)
        liveData.postValue(refItemData)
    }
    fun updateData(index:Int, refItem:RefItem){
        refItemData.set(index,refItem)
    }
    fun getCount():Int{
        return refItemData.size
    }
}