package com.example.takeeat.ui.refrigerator

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.takeeat.databinding.FragmentRefrigeratorBinding

class RefrigeratorFragment : Fragment() {

    private var _binding: FragmentRefrigeratorBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val refrigeratorViewModel =
            ViewModelProvider(this).get(RefrigeratorViewModel::class.java)

        _binding = FragmentRefrigeratorBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textRefrigerator
        refrigeratorViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}