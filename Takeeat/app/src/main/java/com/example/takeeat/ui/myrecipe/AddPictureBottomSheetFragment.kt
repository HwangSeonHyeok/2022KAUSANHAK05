package com.example.takeeat.ui.myrecipe

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.takeeat.R
import com.example.takeeat.databinding.FragmentAddPictureBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class AddPictureBottomSheetFragment(
    private val galleryListener: () -> Unit,
    private val cameraListener: () -> Unit
): BottomSheetDialogFragment() {
    private var _binding: FragmentAddPictureBottomSheetBinding? = null
    private val binding get() = _binding ?: throw IllegalArgumentException("schedule dialog fragment view binding error")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = FragmentAddPictureBottomSheetBinding.inflate(inflater, container, false)?.let {
        _binding = it
        binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        openGallery()
        openCamera()
    }

    private fun openGallery() {
        binding.textviewGallery.setOnClickListener {
            galleryListener()
            dismiss()
        }
    }

    private fun openCamera() {
        binding.textviewCamera.setOnClickListener {
            cameraListener()
            dismiss()
        }
    }

    override fun getTheme() = R.style.BottomSheetDialogTheme
}