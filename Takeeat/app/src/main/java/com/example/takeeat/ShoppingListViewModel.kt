package com.example.takeeat

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.takeeat.ui.refrigerator.RefItem

class ShoppingListViewModel : ViewModel() {
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
    fun updateData(index:Int, refItem: RefItem){
        refItemData.set(index,refItem)
    }
    fun getCount():Int{
        return refItemData.size
    }
}