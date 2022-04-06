package com.example.takeeat.ui.menu

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.amazonaws.mobile.client.AWSMobileClient
import com.amazonaws.mobile.client.Callback
import com.amazonaws.mobile.client.UserStateDetails
import com.example.takeeat.AuthActivity
import com.example.takeeat.databinding.FragmentMenuBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton



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
                        //finish()
                    }

                    override fun onError(e: Exception) {}
                })

        }


        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}