package com.example.takeeat.ui.myrecipe

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.TextView
import androidx.databinding.DataBindingUtil.setContentView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.takeeat.NotificationActivity
import com.example.takeeat.R
import com.example.takeeat.ShoppingListActivity
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


        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.removeItem(R.id.app_bar_search_refrigerator)
        menu.removeItem(R.id.app_bar_search_recipe)
        menu.removeItem(R.id.app_bar_search_myrecipe)
        menu.removeItem(R.id.cart_button)
        menu.removeItem(R.id.notification_button)
        inflater.inflate(R.menu.search_menu, menu)

        val searchButtonRefrigerator = menu.findItem(R.id.app_bar_search_refrigerator)
        val searchButtonRecipe = menu.findItem(R.id.app_bar_search_recipe)
        searchButtonRefrigerator.isVisible = false
        searchButtonRecipe.isVisible = false

        menu.findItem(R.id.cart_button).setOnMenuItemClickListener(MenuItem.OnMenuItemClickListener {

            val shoppingintent: Intent = Intent(context, ShoppingListActivity::class.java)
            startActivity(shoppingintent)
            true
        })

        menu.findItem(R.id.notification_button).setOnMenuItemClickListener(MenuItem.OnMenuItemClickListener {

            val notificationintent: Intent = Intent(context, NotificationActivity::class.java)
            startActivity(notificationintent)
            true
        })

        return super.onCreateOptionsMenu(menu,inflater)
    }


    private fun initViewPager() {
        binding.myrecipeContentVp.adapter = MyRecipeVPAdapter(this)
        TabLayoutMediator(binding.myrecipeContentTb , binding.myrecipeContentVp){tab, position ->
            tab.text = information[position]
        }.attach()
    }

}