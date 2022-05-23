package com.example.takeeat.ui.menu

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.amazonaws.mobile.client.AWSMobileClient
import com.amazonaws.mobile.client.Callback
import com.amazonaws.mobile.client.UserStateDetails
import com.example.takeeat.AuthActivity
import com.example.takeeat.NotificationActivity
import com.example.takeeat.R
import com.example.takeeat.ShoppingListActivity
import com.example.takeeat.databinding.FragmentMenuBinding
import com.example.takeeat.ui.refrigerator.RefItemAppDatabase
import com.example.takeeat.ui.refrigerator.RefItemAppDatabase_Impl
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class MenuFragment: Fragment() {

    private var _binding: FragmentMenuBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val myrecipeViewModel =
            ViewModelProvider(this).get(MenuViewModel::class.java)

        _binding = FragmentMenuBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textMenu
        myrecipeViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }

        val logoutbutton : Button = binding.logoutbutton
        logoutbutton.setOnClickListener {
            //여기다 auth값을 false로 하거나 계정관련 변수를 초기화 해주세요
            AWSMobileClient.getInstance().initialize(
                logoutbutton.context,
                object : Callback<UserStateDetails?> {
                    override fun onResult(userStateDetails: UserStateDetails?) {
                        // 로그아웃 후 로그인 창으로 이동
                        AWSMobileClient.getInstance().signOut()
                        val i = Intent(logoutbutton.context, AuthActivity::class.java)
                        startActivity(i)
                        activity!!.finish()
                    }

                    override fun onError(e: Exception) {}
                })

        }

        var refDB = context?.let { RefItemAppDatabase.getDatabase(it) }

        binding.testbutton.setOnClickListener {
            GlobalScope.launch(Dispatchers.IO) {
                if (refDB != null) {
                    Log.d("refDB", refDB.refdbDao().getAll().toString())
                }
            }
        }

        setHasOptionsMenu(true)

        return root
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
        val searchButtonMyrecipe = menu.findItem(R.id.app_bar_search_myrecipe)
        searchButtonRefrigerator.isVisible = false
        searchButtonRecipe.isVisible = false
        searchButtonMyrecipe.isVisible = false

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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}