package com.example.takeeat.ui.refrigerator


import java.io.Serializable
import java.util.*

data class RefItem(
    var itemname : String?,
    var itemtag : String?,
    var itemexp : Date?,
    var itemamount : Int?,
    var itemunit : String?
) : Serializable
