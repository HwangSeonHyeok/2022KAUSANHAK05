package com.example.takeeat.ui.recipe

import android.content.res.TypedArray
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.takeeat.R

class RecipeSearchIngreAdapter(private val itemTestList: MutableList<String>) : RecyclerView.Adapter<RecipeSearchIngreAdapter.IconViewHolder>() {
    private var selectedPos = RecyclerView.NO_POSITION
    lateinit var categoryIconArray : TypedArray
    lateinit var ingreTagArray: Array<String>
    //private var selectedItem: String = "null"
    public var selectedItems = mutableListOf<String>()
    inner class IconViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener{
        val txtItemName: TextView = itemView.findViewById(R.id.recipe_search_item_name_icon)
        val imgItem: ImageView = itemView.findViewById(R.id.recipe_search_item_image_icon)
        //val itemCard: CardView = itemView.findViewById(R.id.recipe_search_item_card)
        override fun onClick(view: View?) {
            notifyItemChanged(selectedPos)
            selectedPos = layoutPosition
            if(!selectedItems.contains(itemTestList[this.absoluteAdapterPosition])) {
                selectedItems.add(itemTestList[this.absoluteAdapterPosition])
            }
            else selectedItems.remove(itemTestList[this.absoluteAdapterPosition])
            notifyItemChanged(selectedPos)
        }
        init{
            categoryIconArray =
                itemView.context.resources.obtainTypedArray(R.array.IngreIconArray)
            ingreTagArray =itemView.context.resources.getStringArray(R.array.RefrigeratorItemTagArray)
            itemView.setOnClickListener {onClick(itemView)}
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeSearchIngreAdapter.IconViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_recipe_search, parent, false)
        return IconViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RecipeSearchIngreAdapter.IconViewHolder, position: Int) {
        val rowPos = holder.absoluteAdapterPosition
        val items = itemTestList[rowPos]

        holder.apply {
            txtItemName.setSelected(selectedItems.contains(items))
            if(items.length!! > 5) {
                txtItemName.text = items.substring(0, items.length.coerceAtMost(4)) + "…"
            }
            else
            {
                txtItemName.text = items
            }
            if(txtItemName.isSelected){
                txtItemName.setTextColor(Color.parseColor("#FFFFFF"))
            }
            else txtItemName.setTextColor(Color.parseColor("#000000"))
            imgItem.setImageDrawable(categoryIconArray.getDrawable(ingreTagArray.indexOf(items)))
        }
    }

    override fun getItemCount(): Int {
        return itemTestList.size
    }
}