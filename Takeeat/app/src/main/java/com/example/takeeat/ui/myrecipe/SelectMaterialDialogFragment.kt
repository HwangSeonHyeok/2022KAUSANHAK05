package com.example.takeeat.ui.myrecipe

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import androidx.fragment.app.DialogFragment
import com.example.takeeat.R
import com.example.takeeat.databinding.FragmentSelectMaterialDialogBinding
import com.example.takeeat.ui.myrecipe.adapter.SelectMaterialListAdapter

class SelectMaterialDialogFragment(val listener: (String) -> Unit): DialogFragment() {
    private var _binding: FragmentSelectMaterialDialogBinding? = null
    private val binding get() = _binding ?: throw IllegalArgumentException("schedule dialog fragment view binding error")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSelectMaterialDialogBinding.inflate(inflater, container, false)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)
        binding.root.clipToOutline = true
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setMaterialList()
    }

    override fun onResume() {
        super.onResume()
    }

    private fun setMaterialList() {
        binding.recyclerviewMaterial.run {
            adapter = SelectMaterialListAdapter {
                listener(it)
                dismiss()
            }
            (adapter as SelectMaterialListAdapter).submitList(resources.getStringArray(R.array.RefrigeratorItemTagArray).toList())
        }
    }
}