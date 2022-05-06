package com.example.takeeat.ui.recipe

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.MediaController
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.takeeat.RecipeItem
import com.example.takeeat.databinding.ActivityRecipedetailBinding

class RecipeDetailActivity : AppCompatActivity() {

    private lateinit var binding : ActivityRecipedetailBinding
    private lateinit var recipeItem : RecipeItem
    private lateinit var ingreAdapter : RecipeDetailIngreAdapter
    private lateinit var recipeStepAdapter : RecipeStepAdapter
    var amount : Int  = 1;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRecipedetailBinding.inflate(layoutInflater)
        recipeItem = intent.getSerializableExtra("Recipe_Data") as RecipeItem
        Glide.with(this).load(recipeItem.imgURL).into(binding.recipedetailMainImage)
        binding.recipedetailRecipeName.text = recipeItem.recipeName
        binding.recipedetailRecipeWriter.text = recipeItem.recipeWriter
        binding.recipedetailRecipeSummary.text = recipeItem.recipeSummary
        ingreAdapter = RecipeDetailIngreAdapter(recipeItem.recipeIngredients)
        binding.recipedetailIngreRecyclerView.adapter = ingreAdapter
        binding.recipedetailDifficultyText.text = recipeItem.recipeDifficulty
        binding.recipedetailTimeText.text = recipeItem.recipeTime
        val recipeViewPager = binding.recipedetailRecipeStepViewPager
        if(recipeItem.recipeStep!=null) {
            recipeStepAdapter = RecipeStepAdapter(recipeItem.recipeStep!!)
            recipeStepAdapter.setRecipeIngre(recipeItem.recipeIngredients)
            recipeViewPager.adapter = recipeStepAdapter
        }

        binding.recipedetailRecipeBookmark.setOnClickListener {
            //이거 누르면 레시피 구독
        }
        binding.recipedetailWriterBookmark.setOnClickListener{
            //이거 누a르면 작성자 구독
        }

        binding.recipedetailMinusButton.setOnClickListener {
            if(amount > 1){
                amount--;
            }
        }
        binding.recipedetailPlusButton.setOnClickListener {
            if(amount < 20){
                amount++;
            }
        }


        /*binding.recipedetailVideoView.setMediaController(MediaController(this))
        binding.recipedetailVideoView.setVideoURI(Uri.parse(""))
        binding.recipedetailVideoView.requestFocus()
        binding.recipedetailVideoView.setOnPreparedListener {
            Toast.makeText(applicationContext, "준비 완료", Toast.LENGTH_SHORT).show()
            binding.recipedetailVideoView.start()
        }*/
        setContentView(binding.root)

    }
}