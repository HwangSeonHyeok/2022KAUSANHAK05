package com.example.takeeat.ui.recipe

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.takeeat.R
import com.example.takeeat.RecipeProcess

class RecipeStepAdapter(data: ArrayList<RecipeProcess>): RecyclerView.Adapter<RecipeStepAdapter.PagerViewHolder>() {
    val recipeData = data
    class PagerViewHolder(parent : ViewGroup) : RecyclerView.ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_recipestep, parent, false)){
        val stepText : TextView
        val explanation : TextView
        val image : ImageView
        init{
            stepText = itemView.findViewById((R.id.recipestep_stepText))
            explanation = itemView.findViewById(R.id.recipestep_explanation)
            image = itemView.findViewById(R.id.recipestep_image)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagerViewHolder {
        return PagerViewHolder(parent)
    }

    override fun onBindViewHolder(holder: PagerViewHolder, position: Int) {
        recipeData.get(position).let { item ->
            with(holder) {
                stepText.text = "Step "+(this.absoluteAdapterPosition+1).toString()+"/"+itemCount.toString()

                explanation.text = item.recipeExplanation
                Glide.with(holder.image.context).load(item.imgURL).into(image)
            }
        }

    }

    override fun getItemCount(): Int {
        return recipeData.size
    }
}