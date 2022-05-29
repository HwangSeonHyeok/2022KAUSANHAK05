package com.example.takeeat.ui.recipe

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
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
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        val resultRecyclerView = binding.recipeSearchResultRecipeRecyclerView
        if(recipeArray.size == 0){
            resultRecyclerView.visibility = View.INVISIBLE
            binding.recipeSearchResultNoResultText.visibility = View.VISIBLE
        }
        recipeArray.sortByDescending {it.recipeId}
        recipeItemAdapter = RecipeItemAdapter(recipeArray)
        resultRecyclerView.adapter = recipeItemAdapter
        binding.recipeSearchResultSortRadioGroup.setOnCheckedChangeListener { radioGroup, i ->
            when(i){
                R.id.recipeSearchResult_sortLatelyButton -> {
                    recipeArray.sortByDescending {it.recipeId}
                    (resultRecyclerView.adapter as RecipeItemAdapter).resetVisibleItemCount()
                    resultRecyclerView.scrollToPosition(0)
                }
                R.id.recipeSearchResult_sortRatingButton -> {
                    recipeArray.sortByDescending { it.recipeRating }
                    (resultRecyclerView.adapter as RecipeItemAdapter).resetVisibleItemCount()
                    resultRecyclerView.scrollToPosition(0)
                }
            }
            Log.d("Response",recipeArray.toString())

            resultRecyclerView.adapter!!.notifyDataSetChanged()
        }
        resultRecyclerView.addOnScrollListener(
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.getItemId()) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }


}