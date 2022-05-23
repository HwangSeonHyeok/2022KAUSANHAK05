package com.example.takeeat.ui.myrecipe

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.takeeat.RecipeItemAdapter
import com.example.takeeat.databinding.FragmentMyrecipeBinding
import com.example.takeeat.databinding.FragmentbookmarkBinding

class BookMarkFragment :Fragment() {
    private lateinit var binding: FragmentbookmarkBinding


    // This property is only valid between onCreateView and
    // onDestroyView.

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentbookmarkBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        fetchBookmarkList()
    }

    private fun fetchBookmarkList() {
        // 서버통신 후 recyclerview data 더미데이터 대신 집어넣기
        binding.recyclerviewBookmarkList.adapter = RecipeItemAdapter(CreatedFragment.dummyRecipeList)
    }
}