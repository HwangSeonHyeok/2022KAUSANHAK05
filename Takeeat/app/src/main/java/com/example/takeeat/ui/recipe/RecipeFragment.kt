package com.example.takeeat.ui.recipe

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.takeeat.databinding.FragmentRecipeBinding

class RecipeFragment : Fragment() {

    private var _binding: FragmentRecipeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val recipeViewModel =
            ViewModelProvider(this).get(RecipeViewModel::class.java)

        _binding = FragmentRecipeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textRecipe
        recipeViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        //로그아웃 임시버튼
        val logoutbutton : Button = binding.logoutbutton
        logoutbutton.setOnClickListener {
            //여기다 auth값을 false로 하거나 계정관련 변수를 초기화 해주세요

        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}