package com.example.takeeat.ui.myrecipe

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil.setContentView
import androidx.fragment.app.Fragment
import com.example.takeeat.R
import com.example.takeeat.databinding.FragmentMyrecipeBinding
import com.example.takeeat.databinding.FragmentbookmarkBinding
import com.example.takeeat.databinding.FragmentCreatedBinding
import io.reactivex.disposables.CompositeDisposable

class CreatedFragment :Fragment() {
    private lateinit var binding: FragmentCreatedBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCreatedBinding.inflate(inflater, container, false)
        binding.recipeUploadButton.setOnClickListener{
            val intent= Intent (this@CreatedFragment.requireContext(), RecipeUpload::class.java)
            startActivity(intent)
        }

       /* val uploadbutton= view?.findViewById<Button>(R.id.recipe_upload_button)
        uploadbutton?.setOnClickListener(({
            val intent= Intent(context, RecipeUpload::class.java)
            startActivity(intent)
            activity?.finish()
        }))
*/


        return binding.root
    }



}

