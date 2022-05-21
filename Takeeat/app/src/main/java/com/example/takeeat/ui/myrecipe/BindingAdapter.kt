package com.example.takeeat.ui.myrecipe

import android.net.Uri
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.Spinner
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import com.bumptech.glide.Glide
import com.example.takeeat.R

object BindingAdapter {

    @JvmStatic
    @BindingAdapter(value = ["spinnerEntries", "itemLayout"], requireAll = false)
    fun Spinner.setEntries( entries: Array<String>, itemLayout: Int) {
        val arrayAdapter = ArrayAdapter(context, itemLayout, entries)
        arrayAdapter.setDropDownViewResource(itemLayout)
        adapter = arrayAdapter
    }

    @JvmStatic
    @BindingAdapter("setRecipeOrderImage")
    fun ImageView.setRecipeOrderImage(uri: Uri?) {
        if (uri != null) {
            Glide.with(this.context).load(uri).into(this)
        } else {
            setImageResource(R.drawable.ic_baseline_camera)
        }
    }

    @InverseBindingAdapter(attribute = "selectedValue", event = "selectedValueAttrChanged")
    @JvmStatic
    fun Spinner.getSelectedValue(): Any? {
        return selectedItem
    }

    @BindingAdapter("selectedValueAttrChanged")
    @JvmStatic
    fun Spinner.setInverseBindingListener(inverseBindingListener: InverseBindingListener?) {
        inverseBindingListener?.run {
            onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View,
                    position: Int,
                    id: Long
                ) {
                    if (tag != position) {
                        inverseBindingListener.onChange()
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>) {}
            }
        }
    }

    @JvmStatic
    @BindingAdapter("selectedValue")
    fun Spinner.setSelectedValue(selectedValue: Any?) {
        adapter?.run {
            val position = (adapter as ArrayAdapter<Any>).getPosition(selectedValue)
            setSelection(position, false)
            tag = position
        }
    }
}