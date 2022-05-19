package com.example.takeeat.ui.recipe

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.takeeat.AppDatabase
import com.example.takeeat.IngredientsInfo
import com.example.takeeat.R

class RecipeRecommendBlockAdapter(var data: ArrayList<RecipeBlock>):  RecyclerView.Adapter<RecipeRecommendBlockAdapter.ViewHolder>() {


    inner class ViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(
            R.layout.item_recommendblock, parent, false
        )
    ) {
        val blockName: TextView
        val recommendRecipeRecyclerView: RecyclerView

        init {
            blockName = itemView.findViewById(R.id.recommendblock_BlockName)
            recommendRecipeRecyclerView = itemView.findViewById(R.id.recommendblock_RecyclerView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        data.get(position).let { item ->
            with(holder) {
                blockName.text = item.blockName
                Log.d("Response","test:"+item.recommendList.toString())
                val adapter = RecipeRecommendAdapter(item.recommendList)
                recommendRecipeRecyclerView.adapter = adapter

            }
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

}