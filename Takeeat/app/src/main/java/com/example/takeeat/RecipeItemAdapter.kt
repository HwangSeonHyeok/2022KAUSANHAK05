package com.example.takeeat

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.takeeat.ui.refrigerator.AddRefrigeratorAdapter
import org.w3c.dom.Text
import java.util.*

class RecipeItemAdapter(var data: ArrayList<RecipeItem>):  RecyclerView.Adapter<RecipeItemAdapter.ViewHolder>() {
    class ViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_recipe, parent,false)),View.OnClickListener{
        val recipeName: TextView
        val recipeImage : ImageView
        val recipeIngredient: TextView
        val recipeIntroduce : TextView
        val recipeRate : TextView
        val recipeTime : TextView
        val recipeDifficulty : TextView
        val recipeWriter: TextView

        override fun onClick(p0: View?) {
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
            recipeName.text = data[position].recipeName
            //recipeImage
            //recipeIngredient= data[position].recipeIngredients[0]
            recipeIntroduce.text = data[position].recipeIntroduce
            recipeRate.text = data[position].recipeRating.toString()
            recipeTime.text = data[position].recipeTime.toString()+"분"
            recipeDifficulty.text = data[position].recipeDifficulty
            recipeWriter.text = data[position].recipeWriter
        }




    }


}