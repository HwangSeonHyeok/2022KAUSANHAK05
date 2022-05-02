package com.example.takeeat.ui.recipe

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.takeeat.IngredientsInfo
import com.example.takeeat.R

import kotlin.collections.ArrayList

class RecipeDetailIngreAdapter(var data: ArrayList<IngredientsInfo>):  RecyclerView.Adapter<RecipeDetailIngreAdapter.ViewHolder>() {
    class ViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_recipeingre, parent, false)) {
        val ingreName: TextView
        val ingreCount: TextView
        val ingreInMyRef: TextView


        init {
            ingreName = itemView.findViewById(R.id.itemrecipeingre_name)
            ingreCount = itemView.findViewById(R.id.itemrecipeingre_amount)
            ingreInMyRef = itemView.findViewById(R.id.itemrecipeingre_refAmount)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        data.get(position).let { item ->
            with(holder) {
                Log.d("Response","IngreAdapter"+item.ingreName)
                ingreName.text = item.ingreName
                if(item.ingreCount!=null)
                    ingreCount.text = item.ingreCount.toString() + item.ingreUnit
                else
                    ingreCount.text = item.ingreUnit
                ingreInMyRef.text = "0개" // user냉장고 안에 ingreName 태그, ingreUnit이 일치하는 품목이 있는지 확인하고 그 수의 합을 여기 저장
            }
        }

    }

    override fun getItemCount(): Int {
        return data.size
    }
}