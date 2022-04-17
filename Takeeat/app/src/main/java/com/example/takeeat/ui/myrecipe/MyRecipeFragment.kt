package com.example.takeeat.ui.myrecipe

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.takeeat.databinding.FragmentMyrecipeBinding
import com.example.takeeat.ui.myrecipe.adapter.MyRecipeVPAdapter
import com.google.android.material.tabs.TabLayoutMediator

class MyRecipeFragment : Fragment() {

    private lateinit var binding: FragmentMyrecipeBinding
    private val information = arrayListOf("구독(북마크)", "마이 레시피")

    // This property is only valid between onCreateView and
    // onDestroyView.

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMyrecipeBinding.inflate(inflater, container, false)
        val myrecipeViewModel =
            ViewModelProvider(this).get(MyRecpieViewModel::class.java)

        initViewPager()
//
//        myrecipeViewModel.text.observe(viewLifecycleOwner) {
//            binding.textMyrecipe.text = it
//        }



        return binding.root
    }

    private fun initViewPager() {
        binding.myrecipeContentVp.adapter = MyRecipeVPAdapter(this)
        TabLayoutMediator(binding.myrecipeContentTb , binding.myrecipeContentVp){tab, position ->
            tab.text = information[position]
        }.attach()
    }

}