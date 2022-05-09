package com.example.takeeat

import org.json.JSONArray
import java.io.Serializable
import java.net.URL

data class RecipeItem (
    val recipeId:String,
    val recipeName:String,
    val recipeIngredients :ArrayList<IngredientsInfo>,
    val recipeSummary:String,
    val recipeRating:Double,
    val recipeTime:String,
    val recipeDifficulty:String,
    val recipeWriter:String?,
    val imgURL:URL?,
    val recipeStep : ArrayList<RecipeProcess>?,
    val recipeIngredientsSearch: ArrayList<String>,
    val recipeServing: String
) : Serializable
data class RecipeProcess(
    val recipeExplanation:String,
    val imgURL:URL?
) : Serializable
data class IngredientsInfo(
    val ingreName:String,
    val ingreCount:Double?,
    val ingreUnit:String
) : Serializable