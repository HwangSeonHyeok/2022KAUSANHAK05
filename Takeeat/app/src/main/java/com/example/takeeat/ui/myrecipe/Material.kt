package com.example.takeeat.ui.myrecipe

import com.example.takeeat.ui.myrecipe.data.RecipeIngre

class Material (var title:String, var amount:String, var unit:String) {
    fun toRecipeIngre(): RecipeIngre {
        return RecipeIngre(title, amount, unit)
    }
}


fun Material.setTitle(title: String) {
    this.title = title
}

val Material.title: String
    get() {
        return title
    }


fun Material.setAmount(amount: String) {
    this.amount = amount
}
/*

val Material.amount: String
    get(){
        return amount
    }

*/

fun Material.setUnit(unit: String){
    this.unit=unit
}

val Material.unit: String
    get(){return unit}
