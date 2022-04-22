package com.example.takeeat

import org.json.JSONArray
import java.net.URL

data class RecipeItem (
    val recipeId:String,
    val recipeName:String,
    val recipeIngredients : JSONArray,
    val recipeIntroduce:String,
    val recipeRating:Double,
    val recipeTime : String,
    val recipeDifficulty:String,
    val recipeWriter:String?,
    val imgURL : URL?
        )