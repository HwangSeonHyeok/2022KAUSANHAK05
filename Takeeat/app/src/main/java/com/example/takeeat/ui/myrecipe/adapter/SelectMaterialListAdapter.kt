package com.example.takeeat.ui.myrecipe.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.takeeat.BR
import com.example.takeeat.databinding.FragmentSelectMaterialDialogBinding
import com.example.takeeat.databinding.ItemSelectMaterialBinding
import com.example.takeeat.ui.myrecipe.DiffCallback

class SelectMaterialListAdapter(val listener: (String) -> Unit):ListAdapter<String, SelectMaterialListAdapter.SelectMaterialListViewHolder>(
    DiffCallback<String>()
) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SelectMaterialListViewHolder {
        val binding = ItemSelectMaterialBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SelectMaterialListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SelectMaterialListViewHolder, position: Int) {
        val item = getItem(position)
        holder.binding.run {
            setVariable(BR.name, item)
            root.setOnClickListener {
                listener(item)
            }
        }
    }

    class SelectMaterialListViewHolder(val binding: ItemSelectMaterialBinding): RecyclerView.ViewHolder(binding.root)
}