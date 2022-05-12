package com.example.takeeat.ui.recipe

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.takeeat.IngredientsInfo
import com.example.takeeat.R
import com.example.takeeat.ui.refrigerator.RefItem

class SubtractRefAdapter (var data: LiveData<ArrayList<SubtractRefItem>>):  RecyclerView.Adapter<SubtractRefAdapter.ViewHolder>() {

    class ViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(
            R.layout.item_subtractref, parent, false
        )
    ) {
        val ingreName: TextView
        val refSpinner: Spinner
        val refSeekBar: SeekBar
        val textMax: TextView
        val seekBarText: TextView

        init {

            ingreName = itemView.findViewById(R.id.subrefitem_ingreName)
            refSpinner = itemView.findViewById(R.id.subrefitem_refSpinner)
            refSeekBar = itemView.findViewById(R.id.subrefitem_refSeekBar)
            textMax = itemView.findViewById(R.id.subrefItem_textMax)
            seekBarText = itemView.findViewById(R.id.subrefitem_seekBarText)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        data.value!!.get(position).let { item ->
            with(holder) {
                ingreName.text = item.itemName
                val spinnerUnitItem = ArrayList<String>()
                for (i in item.itemInMyRef) {
                    val itemexp = i.itemexp!!
                    spinnerUnitItem.add(i.itemname!! + " 유통기한:" + itemexp.year.toString() + "." + (itemexp.month + 1).toString() + "." + itemexp.date.toString())
                }
                val spinnerAdapter: ArrayAdapter<String> =
                    ArrayAdapter(
                        holder.refSpinner.context,
                        android.R.layout.simple_spinner_dropdown_item,
                        spinnerUnitItem
                    )
                refSpinner.adapter = spinnerAdapter
                refSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        p0: AdapterView<*>?,
                        p1: View?,
                        p2: Int,
                        p3: Long
                    ) {
                        val selectedItem = item.itemInMyRef[p2]
                        refSeekBar.max = selectedItem.itemamount!!
                        textMax.text = selectedItem.itemamount!!.toString()
                        item.currentProgress=0
                        refSeekBar.progress = item.currentProgress
                        seekBarText.text = 0.toString()
                        item.selectedItemID = selectedItem.itemid.toString()
                    }

                    override fun onNothingSelected(p0: AdapterView<*>?) {
                    }

                }
                refSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                    override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                        seekBarText.text = p1.toString()
                        item.currentProgress=p1
                    }

                    override fun onStartTrackingTouch(p0: SeekBar?) {
                    }

                    override fun onStopTrackingTouch(p0: SeekBar?) {
                    }

                })


            }
        }
    }

    override fun getItemCount(): Int {
        return data.value!!.size
    }
}