package com.example.takeeat

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.takeeat.ui.recipe.RecipeDetailActivity

import java.util.*


class RecipeItemAdapter(var data: ArrayList<RecipeItem>):  RecyclerView.Adapter<RecipeItemAdapter.ViewHolder>() {
    inner class ViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_recipe, parent,false)),View.OnClickListener{
        val recipeName: TextView
        val recipeImage : ImageView
        val recipeIngredient: TextView
        val recipeIntroduce : TextView
        val recipeRate : TextView
        val recipeTime : TextView
        val recipeDifficulty : TextView
        val recipeWriter: TextView

        override fun onClick(view: View?) {
            val intent = Intent(view!!.context, RecipeDetailActivity::class.java)
            val recipeData = data[this.absoluteAdapterPosition]
            intent.putExtra("Recipe_Data",recipeData)
            view.context.startActivity(intent)
        }

        init {
            recipeName = itemView.findViewById(R.id.recipe_item_recipename)
            recipeImage = itemView.findViewById(R.id.recipe_item_Image)
            recipeIngredient = itemView.findViewById(R.id.recipe_item_recipeIngredients)
            recipeIntroduce = itemView.findViewById(R.id.recipe_item_recipeintroduce)
            recipeRate = itemView.findViewById(R.id.recipe_item_rating)
            recipeTime = itemView.findViewById(R.id.recipe_item_time)
            recipeDifficulty = itemView.findViewById(R.id.recipe_item_difficulty)
            recipeWriter = itemView.findViewById(R.id.recipe_item_writer)
            itemView.setOnClickListener {onClick(itemView)}
        }


    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent)
    }




    override fun getItemCount(): Int {
        return data.size

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.apply {
            /*val JJ : JSONArray = data[position].recipeIngredients
            val content = StringBuilder()

            for (i in 0 until JJ.length()){
                val element : JSONObject = JJ.optJSONObject(i)
                content.append(element.optString("ingre_name").toString() + " ")
            }*/
            var ingredientContent:String =""
            for(i in data[position].recipeIngredients){
                ingredientContent += i.ingreName + " "
            }


            recipeName.text = data[position].recipeName
            Glide.with(holder.recipeDifficulty.context).load(data[position].imgURL).into(recipeImage)
            //recipeImage
            //recipeIngredient.text= data[position].recipeIngredients
            recipeIngredient.text= ingredientContent.toString()
            recipeIntroduce.text = data[position].recipeSummary
            recipeRate.text = data[position].recipeRating.toString()
            recipeTime.text = data[position].recipeTime
            recipeDifficulty.text = data[position].recipeDifficulty
            recipeWriter.text = data[position].recipeWriter
        }

    }



}