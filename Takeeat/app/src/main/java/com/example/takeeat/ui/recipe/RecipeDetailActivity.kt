package com.example.takeeat.ui.recipe

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.MediaController
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.takeeat.R
import com.example.takeeat.RecipeItem
import com.example.takeeat.databinding.ActivityRecipedetailBinding

class RecipeDetailActivity : AppCompatActivity() {

    private lateinit var binding : ActivityRecipedetailBinding
    private lateinit var recipeItem : RecipeItem
    private lateinit var ingreAdapter : RecipeDetailIngreAdapter
    private lateinit var recipeStepAdapter : RecipeStepAdapter
    lateinit var inMyRefIngre : ArrayList<Int?>
    var writerbookmarked = false
    var recipebookmarked = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRecipedetailBinding.inflate(layoutInflater)
        recipeItem = intent.getSerializableExtra("Recipe_Data") as RecipeItem
        Glide.with(this).load(recipeItem.imgURL).into(binding.recipedetailMainImage)
        binding.recipedetailRecipeName.text = recipeItem.recipeName
        binding.recipedetailRecipeWriter.text = recipeItem.recipeWriter
        binding.recipedetailRecipeSummary.text = recipeItem.recipeSummary
        ingreAdapter = RecipeDetailIngreAdapter(recipeItem.recipeIngredients)
        //ingreAdapter.inMyRef = inMyRefIngre
        binding.recipedetailIngreRecyclerView.adapter = ingreAdapter
        binding.recipedetailDifficultyText.text = recipeItem.recipeDifficulty
        binding.recipedetailTimeText.text = recipeItem.recipeTime
        //binding.recipedetailAmount.text
        //binding.recipedetailRating.text = recipeItem.recipeRating.toString()
        val recipeViewPager = binding.recipedetailRecipeStepViewPager
        if(recipeItem.recipeStep!=null) {
            recipeStepAdapter = RecipeStepAdapter(recipeItem.recipeStep!!)
            recipeStepAdapter.setRecipeIngre(recipeItem.recipeIngredients)
            recipeViewPager.adapter = recipeStepAdapter
        }

        binding.recipedetailRecipeBookmark.setOnClickListener {
            //이거 누르면 레시피 구독 아래 코드를 태스크 핸들러에 넣으면 될듯 합니다
            if(recipebookmarked) {
                binding.recipedetailRecipeBookmark.setImageResource(R.drawable.ic_baseline_bookmark_border_24)
                Toast.makeText(this,"북마크에서 삭제되었습니다",Toast.LENGTH_SHORT).show()
            }
            else{
                binding.recipedetailRecipeBookmark.setImageResource(R.drawable.ic_baseline_bookmark_24)
                Toast.makeText(this,"북마크에 추가되었습니다",Toast.LENGTH_SHORT).show()
            }
            recipebookmarked = !recipebookmarked
        }
        binding.recipedetailWriterBookmark.setOnClickListener{
            //이거 누르면 작성자 구독 아래 코드를 태스크 핸들러에 넣으면 될듯 합니다
            if(writerbookmarked) {
                binding.recipedetailWriterBookmark.setImageResource(R.drawable.ic_baseline_bookmark_border_24)
                Toast.makeText(this,"북마크에서 삭제되었습니다",Toast.LENGTH_SHORT).show()
            }
            else{
                binding.recipedetailWriterBookmark.setImageResource(R.drawable.ic_baseline_bookmark_24)
                Toast.makeText(this,"북마크에 추가되었습니다",Toast.LENGTH_SHORT).show()
            }
            writerbookmarked = !writerbookmarked
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