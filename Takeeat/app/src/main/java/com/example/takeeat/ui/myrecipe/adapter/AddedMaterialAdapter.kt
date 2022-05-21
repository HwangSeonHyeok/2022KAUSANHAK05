package com.example.takeeat.ui.myrecipe.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.takeeat.BR
import com.example.takeeat.databinding.RecipeMaterialItemBinding
import com.example.takeeat.ui.myrecipe.DiffCallback
import com.example.takeeat.ui.myrecipe.Material

class AddedMaterialAdapter(val listener: (Material) -> Unit): ListAdapter<Material, AddedMaterialAdapter.AddedMaterialViewHolder>(
    DiffCallback<Material>()
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddedMaterialViewHolder {
        val binding = RecipeMaterialItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AddedMaterialViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AddedMaterialViewHolder, position: Int) {
        val material = getItem(position)
        holder.binding.setVariable(BR.material, material)
        removeMaterial(holder)
    }

    private fun removeMaterial(holder: AddedMaterialViewHolder) {
        with(holder.binding) {
            buttonRemove.setOnClickListener {
                edittextMaterialName.clearFocus()
                edittextAmount.clearFocus()
                edittextUnit.clearFocus()
                listener(getItem(holder.absoluteAdapterPosition))
            }
        }
    }


    class AddedMaterialViewHolder(val binding: RecipeMaterialItemBinding): RecyclerView.ViewHolder(binding.root)
}