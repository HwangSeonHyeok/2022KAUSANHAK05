package com.example.takeeat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import com.amazonaws.mobile.client.AWSMobileClient
import com.example.takeeat.ui.refrigerator.RefItem
import org.json.JSONArray
import org.json.JSONObject
import java.io.BufferedReader
import java.io.DataOutputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.util.*

class IntroActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)
        val actionBar = actionBar

        val handler = Handler()
        Thread {
            get_ref_item()
            handler.post{
                val intent = Intent(this, AuthActivity::class.java)
                startActivity(intent)
                finish()
            }
            /*handler.postDelayed(Runnable() {
                val intent = Intent(this, AuthActivity::class.java)
                startActivity(intent)
                finish()
            }, 1500)*/
        }.start()
    }

    override fun onPause() {
        super.onPause()
        finish();
    }

    fun get_ref_item() {
        val itemTestList = ArrayList<RefItem>()

        // 네트워킹 예외처리를 위한 try ~ catch 문
        try {
            val url:URL = URL("http://52.78.228.196/recom/")

            // 서버와의 연결 생성
            val urlConnection = url.openConnection() as HttpURLConnection
            urlConnection.requestMethod = "POST"
            urlConnection.setRequestProperty("Content-Type", "application/json")
            urlConnection.setRequestProperty("Connection","keep-alive")
            urlConnection.setRequestProperty("Accept", "application/json")
            urlConnection.setDoOutput(true)
            urlConnection.setDoInput(true)
            val wr = DataOutputStream(urlConnection.outputStream)
            wr.writeBytes("Test")
            wr.flush()
            wr.close()

            if (urlConnection.responseCode == HttpURLConnection.HTTP_OK) {
                // 데이터 읽기
                // 스트림과 커넥션 해제
                urlConnection.disconnect()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}