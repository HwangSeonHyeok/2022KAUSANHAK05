package com.example.takeeat.ui.recipe

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.takeeat.R
import com.example.takeeat.RecipeItem
import com.example.takeeat.RecipeItemAdapter
import com.example.takeeat.databinding.ActivityRecipeSearchResultBinding

class RecipeSearchResultActivity : AppCompatActivity() {
    private lateinit var binding : ActivityRecipeSearchResultBinding
    lateinit var recipeArray: ArrayList<RecipeItem>
    private lateinit var recipeItemAdapter : RecipeItemAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecipeSearchResultBinding.inflate(layoutInflater)
        recipeArray = intent.getSerializableExtra("Search_Result") as ArrayList<RecipeItem>
        recipeArray.sortBy {it.recipeId}
        recipeItemAdapter = RecipeItemAdapter(recipeArray)
        binding.recipeSearchResultRecipeRecyclerView.adapter = recipeItemAdapter
        binding.recipeSearchResultSortRadioGroup.setOnCheckedChangeListener { radioGroup, i ->
            when(i){
                R.id.recipeSearchResult_sortLatelyButton -> recipeArray.sortBy {it.recipeId}
                R.id.recipeSearchResult_sortRatingButton -> recipeArray.sortBy{it.recipeName}
            }
            Log.d("Response",recipeArray.toString())

            binding.recipeSearchResultRecipeRecyclerView.adapter!!.notifyDataSetChanged()
        }

        setContentView(binding.root)
    }
}