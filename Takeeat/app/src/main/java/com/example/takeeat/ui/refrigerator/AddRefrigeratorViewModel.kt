package com.example.takeeat.ui.refrigerator

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AddRefrigeratorViewModel : ViewModel()  {
    var liveData : MutableLiveData<ArrayList<RefItem>> = MutableLiveData<ArrayList<RefItem>>()
    var refItemData = ArrayList<RefItem>()
    init{
        refItemData.add(RefItem("우유",null, null, 3, null))
        Log.d("Response", "New Data : " +refItemData.get(0).itemname)
        liveData.postValue(refItemData)
    }
    fun addData(refItem: RefItem){
        refItemData.add(refItem)
        liveData.postValue(refItemData)
    }
    fun deleteData(at : Int){
        val deletedData = refItemData.removeAt(at)
        liveData.postValue(refItemData)
    }
}