package com.example.takeeat

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.takeeat.ui.refrigerator.RefItem

class ShoppingListViewModel : ViewModel() {
    var liveData : MutableLiveData<ArrayList<ShoppingListItem>> = MutableLiveData<ArrayList<ShoppingListItem>>()
    var shoppingListItemData = ArrayList<ShoppingListItem>()
    fun addData(shoppingListItem: ShoppingListItem){
        shoppingListItemData.add(shoppingListItem)
        liveData.postValue(shoppingListItemData)
    }
    fun deleteData(at : Int){
        shoppingListItemData.removeAt(at)
        liveData.postValue(shoppingListItemData)
    }
    fun updateData(index:Int, shoppingListItem: ShoppingListItem){
        shoppingListItemData.set(index,shoppingListItem)
    }
    fun getCount():Int{
        return shoppingListItemData.size
    }
}