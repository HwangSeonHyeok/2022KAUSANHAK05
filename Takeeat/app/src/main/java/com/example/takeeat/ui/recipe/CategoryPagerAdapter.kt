package com.example.takeeat.ui.recipe

import android.util.Log
import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.takeeat.R
import java.lang.Math.min

class CategoryPagerAdapter(val refData: ArrayList<String>, val categoryData: Array<String>): RecyclerView.Adapter<CategoryPagerAdapter.PagerViewHolder>() {
    val refPage = (refData.size-1)/8 +1
    val categoryPage = (categoryData.size-1)/8 + 1

    class PagerViewHolder(parent : ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(
            R.layout.item_categorypager, parent, false)) {
        val gridRecyclerView : RecyclerView
        val nothingInRefText : TextView
        init{
            gridRecyclerView = itemView.findViewById(R.id.recipefragment_categoryRecyclerView)
            nothingInRefText = itemView.findViewById(R.id.recipefragment_nothingInRef)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagerViewHolder {
        return PagerViewHolder(parent)
    }

    override fun onBindViewHolder(holder: PagerViewHolder, position: Int) {
        if(refData.size==0&&position==0){
            holder.nothingInRefText.visibility = View.VISIBLE
        }
        else if(refData.size!=0&&position<refPage){
            refData.get(position).let { item ->
                with(holder) {
                    val gridData = ArrayList<String>()
                    for (i in (absoluteAdapterPosition * 8) until min(
                        refData.size,
                        (absoluteAdapterPosition + 1) * 8
                    )) {
                        gridData.add(refData[i])
                    }
                    val adapter = RecipeCategoryAdapter(gridData,0)
                    adapter.page = absoluteAdapterPosition
                    gridRecyclerView.layoutManager = GridLayoutManager(gridRecyclerView.context, 4)
                    gridRecyclerView.adapter = adapter
                }

            }

        }
        else {
            categoryData.get(position).let { item ->
                with(holder) {
                    val gridData = ArrayList<String>()
                    for (i in ((absoluteAdapterPosition-refPage) * 8) until min(
                        categoryData.size,
                        ((absoluteAdapterPosition-refPage) + 1) * 8
                    )) {
                        gridData.add(categoryData[i])
                    }
                    val adapter = RecipeCategoryAdapter(gridData,1)
                    adapter.page = (absoluteAdapterPosition-refPage)
                    gridRecyclerView.layoutManager = GridLayoutManager(gridRecyclerView.context, 4)
                    gridRecyclerView.adapter = adapter
                }

            }
        }
    }

    override fun getItemCount(): Int {
        var pagecount =  refPage+categoryPage
        if(refData.size==0) pagecount--
        return pagecount
    }



}