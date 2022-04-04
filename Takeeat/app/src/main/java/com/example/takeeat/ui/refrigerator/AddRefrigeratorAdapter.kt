package com.example.takeeat.ui.refrigerator

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.takeeat.R

class AddRefrigeratorAdapter(var data: LiveData<ArrayList<RefItem>>):  RecyclerView.Adapter<AddRefrigeratorAdapter.ViewHolder>() {

    class ViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_addref, parent,false)){
        val itemNameEdit: EditText
        val itemTagButton : Button
        val itemEXPText: TextView
        val itemAmmountEdit : EditText
        val itemUnitSpinner : Spinner
        init {
            itemNameEdit = itemView.findViewById(R.id.addref_EditItemName)
            itemTagButton = itemView.findViewById(R.id.addref_TagButton)
            itemEXPText = itemView.findViewById(R.id.addref_EXPText)
            itemAmmountEdit = itemView.findViewById(R.id.editItemAmount)
            itemUnitSpinner = itemView.findViewById(R.id.add_addref_UnitSpinner)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(parent)
    }
    override fun getItemCount(): Int {

        Log.d("Response", "getItemCount() "+ data.value!!.size)
        return data.value!!.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var a = AddRefrigeratorViewModel()

        data.value!!.get(position).let{item ->
            with(holder){
                itemNameEdit.setText(item.itemname)
                if(item.itemamount!=null)
                    itemAmmountEdit.setText(item.itemamount!!)
                val spinnerUnitItem = holder.itemUnitSpinner.context.getResources().getStringArray(R.array.unitArray)
                val spinnerAdapter : ArrayAdapter<String> = ArrayAdapter(holder.itemUnitSpinner.context,android.R.layout.simple_spinner_dropdown_item,spinnerUnitItem)
                holder.itemUnitSpinner.setAdapter(spinnerAdapter)


            }

        }
    }


}