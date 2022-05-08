package com.example.takeeat.ui.recipe

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.Spinner
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.takeeat.IngredientsInfo
import com.example.takeeat.R

class SubtractRefAdapter (var data: ArrayList<IngredientsInfo>):  RecyclerView.Adapter<SubtractRefAdapter.ViewHolder>() {
    class ViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(
            R.layout.item_subtractref, parent, false)) {
        val ingreName: TextView
        val refSpinner: Spinner
        val refSeekBar: SeekBar
        val textMax : TextView
        val seekBarText : TextView


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
        data.get(position).let { item ->
            with(holder) {
                ingreName.text = item.ingreName
                if(item.ingreCount!=null) {
                    refSeekBar.max = (item.ingreCount!! * 2).toInt()
                    refSeekBar.progress = item.ingreCount.toInt()
                    seekBarText.text = refSeekBar.progress.toString()
                    textMax.text  = (item.ingreCount!! * 2).toString()
                    refSeekBar.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener{
                        override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                            seekBarText.text = p1.toString()
                        }

                        override fun onStartTrackingTouch(p0: SeekBar?) {
                        }

                        override fun onStopTrackingTouch(p0: SeekBar?) {
                        }

                    })


                }
                else{
                    refSeekBar.visibility = View.INVISIBLE
                    seekBarText.visibility = View.INVISIBLE
                }

            }
        }

    }

    override fun getItemCount(): Int {
        return data.size
    }
}