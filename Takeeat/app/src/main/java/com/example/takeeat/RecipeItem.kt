package com.example.takeeat

data class RecipeItem (
    val recipeId:String,
    val recipeName:String,
    val recipeIngredients : String,
    val recipeIntroduce:String,
    val recipeRating:Int,
    val recipeTime : Int,
    val recipeDifficulty:String,
    val recipeWriter:String
        )