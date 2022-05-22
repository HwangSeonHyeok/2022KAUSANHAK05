package com.example.takeeat.ui.myrecipe

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class RecipeUploadViewModel: ViewModel() {
    val recipeTitle = MutableLiveData<String>()
    val recipeDescription = MutableLiveData<String>()
    val selectedCategory = MutableLiveData<String>()
    val servingCount = MutableLiveData<String>()
    val cookingTime = MutableLiveData<String>()
    val difficulty = MutableLiveData<String>()
    val amount = MutableLiveData<String>()
    val unit = MutableLiveData<String>()

    private val _thumbnailImage = MutableStateFlow<Uri?>(null)
    val thumbnailImage = _thumbnailImage.asStateFlow()

    private val _thumbnailFilePath = MutableStateFlow<String?>(null)
    val thumbnailFilePath = _thumbnailFilePath.asStateFlow()

    private val _addToThumbnail = MutableStateFlow<Boolean>(true)
    val addToThumbnail = _addToThumbnail.asStateFlow()

    private val _tag = MutableStateFlow<String>("재료 선택")
    val tag = _tag.asStateFlow()

    private val _difficultySpinnerEntries = MutableStateFlow(emptyList<String>())
    val difficultySpinnerEntries = _difficultySpinnerEntries.asStateFlow()

    private val _addedMaterialList = MutableStateFlow<List<Material>>(listOf())
    val addedMaterialList = _addedMaterialList.asStateFlow()

    private val _cookingOrderList = MutableStateFlow<List<Order>>(listOf())
    val cookingOrderList = _cookingOrderList.asStateFlow()

    private val _uploadPictureOrder = MutableStateFlow<Order?>(null)
    val uploadPictureOrder = _uploadPictureOrder.asStateFlow()

    fun selectMaterial(materialName: String) {
        _tag.value = materialName
    }

    fun addMaterial(material: Material) {
        val tempList = addedMaterialList.value.toMutableList()
        tempList.add(material)
        _addedMaterialList.value = tempList
    }

    fun removeMaterial(material: Material) {
        val tempList = addedMaterialList.value.toMutableList()
        tempList.remove(material)
        _addedMaterialList.value = tempList
    }

    fun addEmptyCookingOrder() {
        val tempList = cookingOrderList.value.toMutableList()
        tempList.add(Order(tempList.size + 1, "", null, null))
        _cookingOrderList.value = tempList
    }

    fun deleteCookingOrder(order: Order) {
        val tempList = cookingOrderList.value.toMutableList()
        tempList.remove(order)
        for(i in 0 until tempList.size) {
            val order = tempList[i]
            tempList[i] = Order(i+1, order.text, order.image, order.imageFilePath)
        }
        tempList.map { Order(tempList.indexOf(it) + 1, it.text, it.image, it.imageFilePath) }
        _cookingOrderList.value = tempList
    }

    fun changeOrderDescription(order: Order, description: String) {
        val idx = cookingOrderList.value.indexOf(order)
        _cookingOrderList.value[idx].text = description
    }

    fun changeUploadPictureOrder(order: Order) {
        _uploadPictureOrder.value = order
    }

    fun addImage(uri: Uri, filePath: String?) {
        val targetOrder = uploadPictureOrder.value
        val tempList = cookingOrderList.value.toMutableList()
        val idx = tempList.indexOf(targetOrder)
        tempList[idx] = Order(targetOrder?.order ?: 1, targetOrder?.text ?: "", uri, filePath)
        _cookingOrderList.value = tempList
    }

    fun initUploadPictureOrder() {
        _uploadPictureOrder.value = null
    }

    fun changeIsAddToThumbnail(boolean: Boolean) {
        _addToThumbnail.value = boolean
    }

    fun changeThumbnailImage(uri: Uri, path: String?) {
        _thumbnailImage.value = uri
        if (!path.isNullOrEmpty()) _thumbnailFilePath.value = path
    }

}