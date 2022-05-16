package com.example.takeeat.ui.recipe

import android.R
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.takeeat.IngredientsInfo
import com.example.takeeat.ShoppingListAdapter
import com.example.takeeat.ShoppingListItem
import com.example.takeeat.ShoppingListViewModel
import com.example.takeeat.databinding.ActivitySubtractrefBinding
import com.example.takeeat.ui.refrigerator.RefItem

class SubtractRefActivity : AppCompatActivity() {
    private lateinit var binding : ActivitySubtractrefBinding
    lateinit var ingreList : ArrayList<IngredientsInfo>
    lateinit var inMyRef : ArrayList<RefItem>
    lateinit var subtractRefAdapter: SubtractRefAdapter
    lateinit var viewModel: SubtractRefViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySubtractrefBinding.inflate(layoutInflater)
        ingreList = intent.getSerializableExtra("Ingre_Data") as ArrayList<IngredientsInfo>
        inMyRef = intent.getSerializableExtra("inMyRef") as ArrayList<RefItem>
        viewModel = ViewModelProviders.of(this).get(SubtractRefViewModel::class.java)
        if(viewModel.liveData.value==null) {
            for (ingre in ingreList) {
                val refItemSameTag = ArrayList<RefItem>()
                if (ingre.ingreCount != null) {
                    for (refItem in inMyRef) {
                        if (ingre.ingreName.contains(refItem.itemtag!!)) {
                            refItemSameTag.add(refItem)
                        }
                    }
                    if (refItemSameTag.size > 0) {
                        /*val spinnerUnitItem = ArrayList<String>()
                    for (i in refItemSameTag) {
                        val itemexp = i.itemexp!!
                        spinnerUnitItem.add(i.itemname!! + " 유통기한:" + itemexp.year.toString() + "." + (itemexp.month + 1).toString() + "." + itemexp.date.toString())
                    }*/
                        val subRefItem = SubtractRefItem(
                            ingre.ingreName + " " + ingre.ingreCount + ingre.ingreUnit,
                            refItemSameTag,
                            refItemSameTag[0].itemamount!!,
                            refItemSameTag[0]
                        )
                        viewModel.addData(subRefItem)
                    }

                }
            }
        }
        setContentView(binding.root)

        val dataObserver: Observer<ArrayList<SubtractRefItem>> =
            Observer {livedata ->
                subtractRefAdapter = SubtractRefAdapter(viewModel.liveData)
                Log.d("Response",viewModel.liveData.value!!.size.toString())
                binding.subrefRecyclerView.adapter = subtractRefAdapter
            }
        viewModel.liveData.observe(this, dataObserver)

        binding.subrefApplyButton.setOnClickListener {
            Log.d("Response",viewModel.liveData.value.toString())

        }
    }

}