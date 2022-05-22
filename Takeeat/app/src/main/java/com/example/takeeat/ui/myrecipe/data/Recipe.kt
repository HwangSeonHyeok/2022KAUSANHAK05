package com.example.takeeat.ui.myrecipe.data

import com.google.gson.annotations.SerializedName

data class Recipe(
    @SerializedName(value = "name") val name : String,
    @SerializedName(value = "img") val img : String?,
    @SerializedName(value = "summary") val summary : String,
    @SerializedName(value = "category") val category : String,
    @SerializedName(value = "cooktime") val cooktime : String,
    @SerializedName(value = "serving") val serving : String,
    @SerializedName(value="difficulty") val difficulty : String,
    @SerializedName(value = "ingre") val ingre : List<RecipeIngre>,
    @SerializedName(value = "recipe") val recipe : List<DetailRecipe>

)

data class RecipeIngre(
    @SerializedName(value = "ingre_name") val ingre_name : String,
    @SerializedName(value = "ingre_count") val ingre_count : String,
    @SerializedName(value = "ingre_unit") val ingre_unit : String
)

data class DetailRecipe(
    @SerializedName(value = "txt") val txt : String,
    @SerializedName(value = "img") val img : String?
)
