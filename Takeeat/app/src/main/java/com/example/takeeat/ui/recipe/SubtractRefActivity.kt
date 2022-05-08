package com.example.takeeat.ui.recipe

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.takeeat.IngredientsInfo
import com.example.takeeat.databinding.ActivitySubtractrefBinding

class SubtractRefActivity : AppCompatActivity() {
    private lateinit var binding : ActivitySubtractrefBinding
    lateinit var ingreList : ArrayList<IngredientsInfo>
    lateinit var subtractRefAdapter: SubtractRefAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySubtractrefBinding.inflate(layoutInflater)
        ingreList = intent.getSerializableExtra("Ingre_Data") as ArrayList<IngredientsInfo>

        subtractRefAdapter = SubtractRefAdapter(ingreList)
        binding.addrefRecyclerView.adapter = subtractRefAdapter


        binding.subrefApplyButton.setOnClickListener {

        }



        setContentView(binding.root)
    }

}