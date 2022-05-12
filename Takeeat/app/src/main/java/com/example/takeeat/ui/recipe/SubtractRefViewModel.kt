package com.example.takeeat.ui.recipe

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.takeeat.ui.refrigerator.RefItem

class SubtractRefViewModel : ViewModel() {
    var liveData : MutableLiveData<ArrayList<SubtractRefItem>> = MutableLiveData<ArrayList<SubtractRefItem>>()
    var subTractRefItemData = ArrayList<SubtractRefItem>()
    fun addData(subRefItem: SubtractRefItem){
        subTractRefItemData.add(subRefItem)
        liveData.postValue(subTractRefItemData)
    }
    fun deleteData(at : Int){
        subTractRefItemData.removeAt(at)
        liveData.postValue(subTractRefItemData)
    }
    fun updateData(index:Int, subRefItem: SubtractRefItem){
        subTractRefItemData.set(index,subRefItem)
    }
    fun getCount():Int{
        return subTractRefItemData.size
    }

}