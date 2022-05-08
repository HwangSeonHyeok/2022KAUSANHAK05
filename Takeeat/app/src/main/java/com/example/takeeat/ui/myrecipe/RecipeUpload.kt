package com.example.takeeat.ui.myrecipe


import android.os.Bundle
import kotlin.collections.ArrayList
import androidx.appcompat.app.AppCompatActivity
import com.example.takeeat.R

class RecipeUpload: AppCompatActivity() {

    var UnitList=arrayListOf<Unit>()
    //var OrderList= arrayListOf<Order>()
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe_upload)
    }


}