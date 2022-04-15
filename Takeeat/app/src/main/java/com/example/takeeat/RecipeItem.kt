package com.example.takeeat

import java.net.URL

data class RecipeItem (
    val recipeId:String,
    val recipeName:String,
    val recipeIngredients : String,
    val recipeIntroduce:String,
    val recipeRating:Double,
    val recipeTime : Int,
    val recipeDifficulty:String,
    val recipeWriter:String?,
    val imgURL : URL?
        )