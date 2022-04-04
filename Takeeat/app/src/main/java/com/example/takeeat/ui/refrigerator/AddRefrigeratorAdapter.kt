package com.example.takeeat.ui.refrigerator

import android.app.DatePickerDialog
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.*
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.takeeat.R
import java.util.*


class AddRefrigeratorAdapter(var data: LiveData<ArrayList<RefItem>>):  RecyclerView.Adapter<AddRefrigeratorAdapter.ViewHolder>() {

    class ViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_addref, parent,false)){
        val itemNameEdit: EditText
        val itemTagButton : Button
        val itemEXPText: TextView
        val itemAmountEdit : EditText
        val itemUnitSpinner : Spinner
        var calendar = Calendar.getInstance()
        var year = calendar.get(Calendar.YEAR)
        var month = calendar.get(Calendar.MONTH)
        var day = calendar.get(Calendar.DAY_OF_MONTH)
        init {
            itemNameEdit = itemView.findViewById(R.id.addref_EditItemName)
            itemTagButton = itemView.findViewById(R.id.addref_TagButton)
            itemEXPText = itemView.findViewById(R.id.addref_EXPText)
            itemAmountEdit = itemView.findViewById(R.id.editItemAmount)
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
        /*val editTextWatcher =object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

            }
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

            }
            override fun afterTextChanged(s: Editable) {

            }
        }*/
        data.value!!.get(position).let{item ->
            with(holder){
                itemNameEdit.setText(item.itemname)
                if(item.itemamount!=null)
                    itemAmountEdit.setText(item.itemamount!!)
                Log.d("Response", item.itemamount.toString())
                itemEXPText.setOnClickListener {
                    val dateSelector = DatePickerDialog(holder.itemEXPText.context, {_, year, month, day ->
                        itemEXPText.setText(year.toString() + "." + (month + 1).toString() + "." + day.toString())
                    },year,month,day)
                    dateSelector.show()
                }
                if(item.itemamount!=null)
                    itemAmountEdit.setText(item.itemamount.toString())
                val spinnerUnitItem = holder.itemUnitSpinner.context.getResources().getStringArray(R.array.unitArray)
                val spinnerAdapter : ArrayAdapter<String> = ArrayAdapter(holder.itemUnitSpinner.context,android.R.layout.simple_spinner_dropdown_item,spinnerUnitItem)
                holder.itemUnitSpinner.setAdapter(spinnerAdapter)
                //holder.itemNameEdit.addTextChangedListener(editTextWatcher)
                //holder.itemAmountEdit.addTextChangedListener(editTextWatcher)


            }

        }
    }


}