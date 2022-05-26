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
            connectURL()
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

    fun connectURL() {
        val itemTestList = ArrayList<RefItem>()

        // 네트워킹 예외처리를 위한 try ~ catch 문
        try {
            val url:URL = URL("http://52.78.228.196/recom/")
            // 서버와의 연결 생성
            val urlConnection = url.openConnection() as HttpURLConnection
            urlConnection.requestMethod = "POST"
            urlConnection.setUseCaches(false)
            //이 아래부분은 http 통신하는 법에 따라서 수정해야함
            urlConnection.setRequestProperty("Content-Type", "application/json")
            urlConnection.setRequestProperty("Connection","keep-alive")
            urlConnection.setRequestProperty("Accept", "application/json")
            urlConnection.setDoOutput(true)
            urlConnection.setDoInput(true)
            val wr = DataOutputStream(urlConnection.outputStream)
            var json = JSONObject()
            json.put("uderId","Test")
            val requestBody = json.toString()
            wr.writeBytes(requestBody)
            wr.flush()
            wr.close()

            if (urlConnection.responseCode == HttpURLConnection.HTTP_OK) {
                Log.d("ResponseConnect","success")
                // 데이터 읽기
                // 스트림과 커넥션 해제
                urlConnection.disconnect()
            }
            else{
                Log.d("ResponseConnect",urlConnection.responseCode.toString())
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Log.d("ResponseConnect","fail")
        }
    }
}