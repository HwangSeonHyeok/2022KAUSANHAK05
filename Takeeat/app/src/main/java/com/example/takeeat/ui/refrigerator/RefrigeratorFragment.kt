package com.example.takeeat.ui.refrigerator

import android.app.Application
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.takeeat.R
import com.example.takeeat.databinding.FragmentRefrigeratorBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton

class RefrigeratorFragment : Fragment() {

    private var _binding: FragmentRefrigeratorBinding? = null


    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    lateinit var mainFab:FloatingActionButton
    lateinit var directFab:FloatingActionButton
    lateinit var galleryFab:FloatingActionButton
    lateinit var cameraFab:FloatingActionButton
    var isFabOpen = false

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
        mainFab= binding.refrigeratorfab
        directFab = binding.directSubFab
        galleryFab = binding.gallerySubFab
        cameraFab = binding.cameraSubFab
        val fabOpen = AnimationUtils.loadAnimation(context, R.anim.fab_open)
        val fabClose = AnimationUtils.loadAnimation(context, R.anim.fab_close)
        val rotateForward = AnimationUtils.loadAnimation(context, R.anim.rotate_forward)
        val rotateBackward = AnimationUtils.loadAnimation(context, R.anim.rotate_backward)


        mainFab.setOnClickListener (View.OnClickListener {
            if(isFabOpen){
                mainFab.startAnimation(rotateForward)
                directFab.startAnimation(fabClose)
                galleryFab.startAnimation(fabClose)
                cameraFab.startAnimation(fabClose)
                directFab.hide()
                directFab.isClickable = false
                galleryFab.hide()
                galleryFab.isClickable = false
                cameraFab.hide()
                cameraFab.isClickable = false
                isFabOpen=false
            }
            else{
                mainFab.startAnimation(rotateBackward)
                directFab.startAnimation(fabOpen)
                galleryFab.startAnimation(fabOpen)
                cameraFab.startAnimation(fabOpen)
                directFab.show()
                directFab.isClickable = true
                galleryFab.show()
                galleryFab.isClickable = false
                cameraFab.show()
                cameraFab.isClickable = false
                isFabOpen=true

            }
        })
        directFab.setOnClickListener (View.OnClickListener {
            val intent = Intent(getActivity(), AddRefrigeratorActivity::class.java)
            startActivity(intent)
        })


        return root
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }



}