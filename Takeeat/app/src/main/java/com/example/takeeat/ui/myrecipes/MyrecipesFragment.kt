package com.example.takeeat.ui.myrecipes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.takeeat.databinding.FragmentMyrecipesBinding

class MyrecipesFragment : Fragment() {

    private var _binding: FragmentMyrecipesBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val myrecipesViewModel =
            ViewModelProvider(this).get(MyrecipesViewModel::class.java)

        _binding = FragmentMyrecipesBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textMyrecipes
        myrecipesViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}