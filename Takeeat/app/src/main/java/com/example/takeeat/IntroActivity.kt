package com.example.takeeat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler

class IntroActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)
        val actionBar = actionBar

        val handler = Handler()
        handler.postDelayed(Runnable(){
            val intent = Intent(this,AuthActivity::class.java)
            startActivity(intent)
            finish()
        },1500)
    }

    override fun onPause() {
        super.onPause()
        finish();
    }
}