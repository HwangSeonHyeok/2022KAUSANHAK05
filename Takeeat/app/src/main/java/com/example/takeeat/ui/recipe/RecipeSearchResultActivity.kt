package com.example.takeeat.ui.recipe

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.takeeat.R
import com.example.takeeat.RecipeItem
import com.example.takeeat.RecipeItemAdapter
import com.example.takeeat.databinding.ActivityRecipeSearchResultBinding
import kotlinx.coroutines.delay
import java.util.logging.Handler

class RecipeSearchResultActivity : AppCompatActivity() {
    private lateinit var binding : ActivityRecipeSearchResultBinding
    lateinit var recipeArray: ArrayList<RecipeItem>
    private lateinit var recipeItemAdapter : RecipeItemAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecipeSearchResultBinding.inflate(layoutInflater)
        recipeArray = intent.getSerializableExtra("Search_Result") as ArrayList<RecipeItem>
        recipeArray.sortByDescending {it.recipeId}
        recipeItemAdapter = RecipeItemAdapter(recipeArray)
        binding.recipeSearchResultRecipeRecyclerView.adapter = recipeItemAdapter
        binding.recipeSearchResultSortRadioGroup.setOnCheckedChangeListener { radioGroup, i ->
            when(i){
                R.id.recipeSearchResult_sortLatelyButton -> {
                    recipeArray.sortByDescending {it.recipeId}
                    (binding.recipeSearchResultRecipeRecyclerView.adapter as RecipeItemAdapter).resetVisibleItemCount()
                    binding.recipeSearchResultRecipeRecyclerView.scrollToPosition(0)
                }
                R.id.recipeSearchResult_sortRatingButton -> {
                    recipeArray.sortByDescending { it.recipeRating }
                    (binding.recipeSearchResultRecipeRecyclerView.adapter as RecipeItemAdapter).resetVisibleItemCount()
                    binding.recipeSearchResultRecipeRecyclerView.scrollToPosition(0)
                }
            }
            Log.d("Response",recipeArray.toString())

            binding.recipeSearchResultRecipeRecyclerView.adapter!!.notifyDataSetChanged()
        }
        binding.recipeSearchResultRecipeRecyclerView.addOnScrollListener(
            object: RecyclerView.OnScrollListener(){
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    val lastViwibleItemPosition = (recyclerView.layoutManager as LinearLayoutManager).findLastCompletelyVisibleItemPosition()
                    val itemTotalCount = recyclerView.adapter!!.itemCount -1
                    if(lastViwibleItemPosition == itemTotalCount&&(recyclerView.adapter as RecipeItemAdapter).visibleItemCount<(recyclerView.adapter as RecipeItemAdapter).data.size){
                        (recyclerView.adapter as RecipeItemAdapter).addVisibleItemCount()

                        recyclerView.adapter!!.notifyItemInserted(recyclerView.adapter!!.itemCount -1)
                    }
                }


            }

        )


        setContentView(binding.root)
    }
}