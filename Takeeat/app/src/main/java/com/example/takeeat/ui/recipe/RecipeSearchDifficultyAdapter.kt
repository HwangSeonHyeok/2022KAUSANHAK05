package com.example.takeeat.ui.recipe

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.takeeat.R

class RecipeSearchDifficultyAdapter(private val itemTestList: Array<String>) : RecyclerView.Adapter<RecipeSearchDifficultyAdapter.IconViewHolder>() {
    private var selectedPos = RecyclerView.NO_POSITION
    public var selectedItem: String? = null
    inner class IconViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener{
        val txtItemName: TextView = itemView.findViewById(R.id.recipe_search_item_name_icon)
        val imgItem: ImageView = itemView.findViewById(R.id.recipe_search_item_image_icon)
        override fun onClick(view: View?) {
            notifyItemChanged(selectedPos)
            if(selectedPos != layoutPosition){
                selectedPos = layoutPosition
                selectedItem = itemTestList[this.absoluteAdapterPosition]
            }
            else {
                selectedPos = RecyclerView.NO_POSITION
                selectedItem = null
            }
            notifyItemChanged(selectedPos)
        }
        init{
            itemView.setOnClickListener {onClick(itemView)}
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeSearchDifficultyAdapter.IconViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_recipe_search, parent, false)
        return IconViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RecipeSearchDifficultyAdapter.IconViewHolder, position: Int) {
        val rowPos = holder.absoluteAdapterPosition
        val items = itemTestList[rowPos]

        holder.apply {
            if(items.length!! > 5) {
                txtItemName.text = items.substring(0, items.length.coerceAtMost(4)) + "…"
            }
            else
            {
                txtItemName.text = items
            }
            txtItemName.setSelected(selectedPos == position)
            if(txtItemName.isSelected){
                txtItemName.setTextColor(Color.parseColor("#FFFFFF"))
            }
            else txtItemName.setTextColor(Color.parseColor("#000000"))
        }
    }

    override fun getItemCount(): Int {
        return itemTestList.size
    }
}


