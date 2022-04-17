package com.example.takeeat.ui.myrecipe.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.takeeat.ui.myrecipe.BookMarkFragment

class MyRecipeVPAdapter(fragment : Fragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> BookMarkFragment()
            else -> BookMarkFragment()
        }

    }
}