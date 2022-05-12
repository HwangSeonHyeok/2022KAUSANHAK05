package com.example.takeeat.ui.recipe

import com.example.takeeat.ui.refrigerator.RefItem

data class SubtractRefItem(
    val itemName : String,
    val itemInMyRef : ArrayList<RefItem>,
    var currentProgress : Int,
    var selectedItemID : String
    )