package com.example.takeeat

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.takeeat.ui.refrigerator.RefItem

class NotificationViewModel : ViewModel() {
    var liveData : MutableLiveData<ArrayList<NotificationItem>> = MutableLiveData<ArrayList<NotificationItem>>()
    var notificationItemData = ArrayList<NotificationItem>()
    fun addData(notificationItem: NotificationItem){
        notificationItemData.add(notificationItem)
        liveData.postValue(notificationItemData)
    }
    fun deleteData(at : Int){
        notificationItemData.removeAt(at)
        liveData.postValue(notificationItemData)
    }
    fun deleteAll(){
        notificationItemData.clear()
        liveData.postValue(notificationItemData)
    }
    fun updateData(index:Int, notificationItem: NotificationItem){
        notificationItemData.set(index,notificationItem)
    }
    fun getCount():Int{
        return notificationItemData.size
    }
}