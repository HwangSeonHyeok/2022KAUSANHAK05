package com.example.takeeat

import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat.getSystemService
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.takeeat.ui.refrigerator.RefItem
import com.example.takeeat.ui.refrigerator.RefrigeratorFragment
import java.util.ArrayList

class ShoppingListAdapter(var data: LiveData<ArrayList<ShoppingListItem>>) : RecyclerView.Adapter<ShoppingListAdapter.ViewHolder>() {
    class ViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_shoppinglist, parent,false)){
        val itemNameText: TextView
        val itemNameEdit : EditText
        val itemMinusButton: AppCompatButton
        val itemCountText : TextView
        val itemPlusButton : AppCompatButton
        init{
            itemNameText = itemView.findViewById(R.id.shoppinglist_ItemName)
            itemNameEdit = itemView.findViewById(R.id.shoppinglist_EditItemName)
            itemMinusButton = itemView.findViewById(R.id.shoppinglist_MinusButton)
            itemCountText = itemView.findViewById(R.id.shoppinglist_count)
            itemPlusButton = itemView.findViewById(R.id.shoppinglist_PlusButton)

        }
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ShoppingListAdapter.ViewHolder {
        return ViewHolder(parent)
    }

    override fun onBindViewHolder(holder: ShoppingListAdapter.ViewHolder, position: Int) {
        val itemNameEditTextWatcher =object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

            }
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            }
            override fun afterTextChanged(s: Editable) {
                data.value!!.get(holder.position).itemname = s.toString()
                Log.d("Response","afterTextChanged")

            }
        }
        data.value!!.get(position).let { item ->
            with(holder) {
                if(data.value!!.get(position).itemname != null)
                    itemNameText.text = data.value!!.get(position).itemname
                if(data.value!!.get(position).itemamount != null)
                    itemCountText.text = data.value!!.get(position).itemamount.toString()+'개'
                val clickListener : View.OnClickListener = View.OnClickListener {

                    when(it){
                        itemNameText ->{
                            itemNameText.visibility=View.INVISIBLE
                            itemNameEdit.visibility=View.VISIBLE
                            itemNameEdit.setText(itemNameText.text)

                        }
                        itemNameEdit ->{
                            data.value!!.get(holder.position).itemname = itemNameEdit.text.toString()
                            itemNameText.visibility=View.VISIBLE
                            itemNameEdit.visibility=View.INVISIBLE
                            itemNameText.setText(itemNameEdit.text)

                            Log.d("Response","enter!")

                        }
                        itemMinusButton->{
                            if(data.value!!.get(position).itemamount != null&& data.value!!.get(position).itemamount!! > 0) {
                                data.value!!.get(position).itemamount = data.value!!.get(position).itemamount!!.minus(1)
                                itemCountText.text = data.value!!.get(position).itemamount.toString()+'개'
                            }
                        }
                        itemPlusButton->{
                            if(data.value!!.get(position).itemamount != null) {
                                data.value!!.get(position).itemamount = data.value!!.get(position).itemamount!!.plus(1)
                                itemCountText.text = data.value!!.get(position).itemamount.toString()+'개'
                            }
                        }
                        itemView->{
                            itemNameText.visibility=View.VISIBLE
                            itemNameEdit.visibility=View.INVISIBLE
                            itemNameText.setText(itemNameEdit.text)

                        }
                    }
                }
                itemNameText.setOnClickListener(clickListener)
                itemNameEdit.setOnClickListener(clickListener)
                itemMinusButton.setOnClickListener(clickListener)
                itemPlusButton.setOnClickListener(clickListener)
                holder.itemNameEdit.addTextChangedListener(itemNameEditTextWatcher)
                itemView.setOnClickListener(clickListener)


            }
        }
    }

    override fun getItemCount(): Int {
        return data.value!!.size
    }

}