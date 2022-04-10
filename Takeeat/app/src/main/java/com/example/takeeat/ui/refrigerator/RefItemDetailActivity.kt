package com.example.takeeat.ui.refrigerator

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import com.example.takeeat.databinding.ActivityRefitemdetailBinding
import java.util.*


class RefItemDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRefitemdetailBinding
    lateinit var refItem: RefItem
    var calendar = Calendar.getInstance()
    var year = calendar.get(Calendar.YEAR)
    var month = calendar.get(Calendar.MONTH)
    var date = calendar.get(Calendar.DAY_OF_MONTH)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRefitemdetailBinding.inflate(layoutInflater)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        refItem = intent.getSerializableExtra("Item_Data") as RefItem
        updateUI(refItem)
        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        binding.refDetailEditButton.setOnClickListener {
            it.visibility = View.INVISIBLE
            it.isClickable = false
            binding.refDetailCompleteButton.visibility = View.VISIBLE
            binding.refDetailCompleteButton.isClickable = true
            binding.refDetailItemname.visibility = View.INVISIBLE
            binding.refDetailNameEdit.visibility = View.VISIBLE
            binding.refDetailNameEdit.setText(binding.refDetailItemname.text)
            binding.refDetailEXP.isClickable = true
            binding.refDetailItemAmount.visibility = View.INVISIBLE
            binding.refDetailEditAmount.visibility = View.VISIBLE
            binding.refDetailEditAmount.setText(binding.refDetailItemAmount.text)

        }
        binding.refDetailCompleteButton.setOnClickListener {
            it.visibility = View.INVISIBLE
            it.isClickable = false
            binding.refDetailEditButton.visibility = View.VISIBLE
            binding.refDetailEditButton.isClickable = true
            binding.refDetailItemname.visibility = View.VISIBLE
            binding.refDetailNameEdit.visibility = View.INVISIBLE
            refItem.itemname = binding.refDetailEditAmount.text.toString()
            binding.refDetailEXP.isClickable = false
            binding.refDetailItemAmount.visibility = View.VISIBLE
            binding.refDetailEditAmount.visibility = View.INVISIBLE
            refItem.itemamount = binding.refDetailEditAmount.text.toString().toInt()
            imm.hideSoftInputFromWindow(binding.refDetailNameEdit.windowToken, 0)
            imm.hideSoftInputFromWindow(binding.refDetailEditAmount.windowToken, 0)
            updateUI(refItem)
        }
        binding.refDetailEXP.setOnClickListener{
            val dateSelector = DatePickerDialog(this, {_, year, month, date ->
                binding.refDetailEXP.setText(year.toString() + "." + (month + 1).toString() + "." + date.toString())
                refItem.itemexp =Date(year,month,date)
            },year,month-1,date)
            dateSelector.show()
        }
        binding.refDetailItemAmount

        setContentView(binding.root)


    }
    fun updateUI(itemData:RefItem){
        binding.refDetailItemname.text = itemData.itemname
        if(itemData.itemexp!=null) {
            year = itemData.itemexp!!.year
            month = itemData.itemexp!!.month
            date = itemData.itemexp!!.date
            binding.refDetailEXP.text = year.toString() + "." + month.toString() + "." + date.toString()
        }
        binding.refDetailItemAmount.text = itemData.itemamount.toString()
        binding.refDetailUnit.text = itemData.itemunit.toString()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.getItemId()) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

}