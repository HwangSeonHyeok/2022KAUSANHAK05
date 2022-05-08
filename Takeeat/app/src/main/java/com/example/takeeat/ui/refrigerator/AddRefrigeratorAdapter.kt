package com.example.takeeat.ui.refrigerator

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.DialogInterface
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.takeeat.R
import java.util.*
import kotlin.collections.ArrayList


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
        var date = calendar.get(Calendar.DAY_OF_MONTH)

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
        return data.value!!.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val context  = holder.itemAmountEdit.context
        val itemNameEditTextWatcher =object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

            }
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            }
            override fun afterTextChanged(s: Editable) {
                if(s.toString()!=null&&s.toString()!="")
                    data.value!!.get(holder.position).itemname = s.toString()

            }
        }
        val itemAmountEditTextWatcher =object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

            }
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            }
            override fun afterTextChanged(s: Editable) {
                if(s.toString()!=null&&s.toString()!="")
                    data.value!!.get(holder.position).itemamount = s.toString().toInt()

            }
        }
        data.value!!.get(position).let{item ->
            with(holder){
                itemNameEdit.setText(item.itemname)
                if(item.itemamount != null)
                    itemAmountEdit.setText(item.itemamount!!.toString())
                if(item.itemexp != null)
                    itemEXPText.setText(item.itemexp!!.getYear().toString() + "." + (item.itemexp!!.getMonth() + 1).toString() + "." + item.itemexp!!.getDate().toString())
                itemEXPText.setOnClickListener {
                    val dateSelector = DatePickerDialog(holder.itemEXPText.context, {_, year, month, date ->
                        itemEXPText.setText(year.toString() + "." + (month + 1).toString() + "." + date.toString())
                        item.itemexp = Date(year,month,date)

                    },year,month,date)
                    dateSelector.show()
                }
                if(item.itemamount!=null)
                    itemAmountEdit.setText(item.itemamount.toString())
                holder.itemTagButton.setOnClickListener {
                    val tags = context.resources.getStringArray(R.array.RefrigeratorItemTagArray)
                    val dialogBuilder = AlertDialog.Builder(context)
                    dialogBuilder.setTitle("태그를 선택해주세요")
                    dialogBuilder.setItems(tags) {
                            p0, p1 ->
                            itemTagButton.text = tags[p1]
                            item.itemtag = tags[p1]

                    }
                    val alertDialog = dialogBuilder.create()
                    alertDialog.show()
                }
                val spinnerUnitItem = context.getResources().getStringArray(R.array.unitArray)
                val spinnerAdapter : ArrayAdapter<String> =
                    ArrayAdapter(context,android.R.layout.simple_spinner_dropdown_item,spinnerUnitItem)
                holder.itemUnitSpinner.setAdapter(spinnerAdapter)
                for(i:Int in 0 until spinnerUnitItem.size) {
                    if(item.itemunit == spinnerUnitItem[i])
                        holder.itemUnitSpinner.setSelection(i)
                }
                holder.itemUnitSpinner.onItemSelectedListener = object:AdapterView.OnItemSelectedListener{
                    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                        item.itemunit = spinnerUnitItem[p2]
                    }

                    override fun onNothingSelected(p0: AdapterView<*>?) {

                    }
                }

                holder.itemNameEdit.addTextChangedListener(itemNameEditTextWatcher)
                holder.itemAmountEdit.addTextChangedListener(itemAmountEditTextWatcher)


            }

        }
    }


}