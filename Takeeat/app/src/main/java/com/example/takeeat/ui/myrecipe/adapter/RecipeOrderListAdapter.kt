package com.example.takeeat.ui.myrecipe.adapter

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.library.baseAdapters.BR
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.takeeat.databinding.RecipeOrderItemBinding
import com.example.takeeat.ui.myrecipe.DiffCallback
import com.example.takeeat.ui.myrecipe.Order


class RecipeOrderListAdapter(private val listener: ChangeContentInterface): ListAdapter<Order, RecipeOrderListAdapter.RecipeOrderListViewHolder>(
    DiffCallback<Order>()
) {
    interface ChangeContentInterface {
        fun writeDescription(order: Order, description: String)
        fun addImage(order: Order)
        fun deleteOrder(order: Order)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeOrderListViewHolder {
        val binding = RecipeOrderItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecipeOrderListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecipeOrderListViewHolder, position: Int) {
        val order = getItem(position)
        holder.binding.setVariable(BR.order, order)
        deleteOrder(holder)
        writeDescription(holder)
        uploadImage(holder)
    }

    private fun writeDescription(holder: RecipeOrderListViewHolder) {
        holder.binding.edittextDescription.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(text: Editable?) {
               listener.writeDescription(getItem(holder.absoluteAdapterPosition), text.toString())
                holder.binding.edittextDescription.clearFocus()
            }
        })
    }

    private fun deleteOrder(holder: RecipeOrderListViewHolder) {
        holder.binding.imageviewDelete.setOnClickListener {
            listener.deleteOrder(getItem(holder.absoluteAdapterPosition))
        }
    }

    private fun uploadImage(holder: RecipeOrderListViewHolder) {
        holder.binding.imageviewPicture.setOnClickListener {
            listener.addImage(getItem(holder.absoluteAdapterPosition))
        }
    }


    class RecipeOrderListViewHolder(val binding: RecipeOrderItemBinding): RecyclerView.ViewHolder(binding.root)
}