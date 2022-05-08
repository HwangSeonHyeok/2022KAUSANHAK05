package com.example.takeeat.ui.recipe

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.takeeat.R
import com.example.takeeat.databinding.ActivityRecipesearchBinding
import com.example.takeeat.ui.refrigerator.RefItem

class RecipeSearchActivity : AppCompatActivity() {

    private lateinit var binding : ActivityRecipesearchBinding
    private lateinit var filteredIngreList: MutableList<String>
    private lateinit var filteredCategoryList: MutableList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecipesearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val recipeIngreList: Array<String> = resources.getStringArray(R.array.RefrigeratorItemTagArray)
        val recipeCategoryList: Array<String> = resources.getStringArray(R.array.CategoryTagArray)
        val difficultyList: Array<String> = resources.getStringArray(R.array.DifficultyArray)

        filteredIngreList = recipeIngreList.toMutableList()
        filteredCategoryList = recipeCategoryList.toMutableList()

        val ingreRecyclerView: RecyclerView = binding.recipeSearchIngreRecyclerview
        val categoryRecyclerView: RecyclerView = binding.recipeSearchCategoryRecyclerview
        val difficultyRecyclerView: RecyclerView = binding.recipeDifficultyRecyclerview
        ingreRecyclerView.layoutManager = GridLayoutManager(this,5)
        categoryRecyclerView.layoutManager = GridLayoutManager(this,5)
        difficultyRecyclerView.layoutManager = GridLayoutManager(this,5)

        ingreRecyclerView.adapter = RecipeSearchIngreAdapter(filteredIngreList)
        categoryRecyclerView.adapter = RecipeSearchCategoryAdapter(filteredCategoryList)
        difficultyRecyclerView.adapter = RecipeSearchDifficultyAdapter(difficultyList)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.recipe_search_menu,menu)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        val recipeSearch = menu.findItem(R.id.recipe_search)

        val recipeIngreList: Array<String> = resources.getStringArray(R.array.RefrigeratorItemTagArray)
        val recipeCategoryList: Array<String> = resources.getStringArray(R.array.CategoryTagArray)


        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        (recipeSearch.actionView as SearchView).apply {
            //Assumes current activity is the searchable activity
            setQuery("", false)
            isIconified = true

            fun filterList(newText:String){
                val newIngreList: MutableList<String> = listOf<String>().toMutableList()
                val newCategoryList: MutableList<String> = listOf<String>().toMutableList()
                val ingreRecyclerView: RecyclerView = binding.recipeSearchIngreRecyclerview
                val categoryRecyclerView: RecyclerView = binding.recipeSearchCategoryRecyclerview
                if(newText != "") {
                    for(x in recipeIngreList){
                        if(x.contains(newText)) newIngreList.add(x)
                    }
                    for(x in recipeCategoryList){
                        if(x.contains(newText)) newCategoryList.add(x)
                    }
                }
                else {
                    for(x in recipeIngreList) newIngreList.add(x)
                    for(x in recipeCategoryList) newCategoryList.add(x)
                }

                if(!newIngreList.isNullOrEmpty()) {
                    filteredIngreList.clear()
                    for(x in newIngreList) filteredIngreList.add(x)
                    //ingreRecyclerView.adapter = RecipeSearchIngreAdapter(filteredIngreList)
                    ingreRecyclerView.adapter?.notifyDataSetChanged()
                }
                if(!newCategoryList.isNullOrEmpty()) {
                    filteredCategoryList.clear()
                    for(x in newCategoryList) filteredCategoryList.add(x)
                    //categoryRecyclerView.adapter = RecipeSearchCategoryAdapter(filteredCategoryList)
                    categoryRecyclerView.adapter?.notifyDataSetChanged()
                }
            }

            setIconifiedByDefault(true)
            queryHint = "검색어를 입력하세요"
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String): Boolean {
                    filterList(query)
                    return false
                }

                override fun onQueryTextChange(newText: String): Boolean {
                    filterList(newText)
                    return false
                }

            })
        }
        return super.onCreateOptionsMenu(menu)
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