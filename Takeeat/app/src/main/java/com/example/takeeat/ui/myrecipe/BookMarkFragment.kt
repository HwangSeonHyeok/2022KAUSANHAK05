package com.example.takeeat.ui.myrecipe

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.amazonaws.mobile.client.AWSMobileClient
import com.example.takeeat.*
import com.example.takeeat.databinding.FragmentMyrecipeBinding
import com.example.takeeat.databinding.FragmentbookmarkBinding
import org.json.JSONArray
import org.json.JSONObject
import java.io.BufferedReader
import java.io.DataOutputStream
import java.io.InputStreamReader
import java.lang.Thread.sleep
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder

class BookMarkFragment :Fragment() {
    private lateinit var binding: FragmentbookmarkBinding
    private var bookmarkRecipe : ArrayList<RecipeItem>? = null
    private var bookmarkList : String? = null


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
        get_bookmark()
    }

    fun get_bookmark() {
        val handler = Handler()
        var bookMark = "{\"bookmark_list\":["

        Thread(Runnable {

            val url: URL = URL("https://b62cvdj81b.execute-api.ap-northeast-2.amazonaws.com/ref-api-test/recipe/bookmark_get")
            var conn: HttpURLConnection = url.openConnection() as HttpURLConnection
            conn.setUseCaches(false)
            conn.setRequestMethod("POST")
            conn.setRequestProperty("Content-Type", "application/json")
            conn.setRequestProperty("Connection", "keep-alive")
            conn.setRequestProperty("Accept", "application/json")
            conn.setDoOutput(true)
            conn.setDoInput(true)

            var job = JSONObject()
            job.put("user_id", AWSMobileClient.getInstance().username)

            var requestBody = job.toString()
            val wr = DataOutputStream(conn.getOutputStream())
            wr.writeBytes(requestBody)
            wr.flush()
            wr.close()

            val streamReader = InputStreamReader(conn.inputStream)
            val buffered = BufferedReader(streamReader)
            val content = StringBuilder()
            while (true) {
                val line = buffered.readLine() ?: break
                content.append(line)
            }
            val data = content.toString()

            buffered.close()
            conn.disconnect()

            val jsonArr = JSONArray(data)
            for (i in 0 until jsonArr.length()) {
                val jsonObj = jsonArr.getJSONObject(i)
                val bmi = URLEncoder.encode(jsonObj.getString("bookmark_id").toString(), "UTF-8")
                bookMark = bookMark + "\"" + bmi + "\","
            }

            if (bookMark != "{\"bookmark_list\":[") {
                bookMark = bookMark.substring(0, bookMark.length - 1) + "]}"
            }

            if(bookMark!="{\"bookmark_list\":[") {
                val recipeTestList = ArrayList<RecipeItem>()
                val url1: URL = URL("https://b62cvdj81b.execute-api.ap-northeast-2.amazonaws.com/ref-api-test/recipe/getbookmarkrecipe")
                var conn1: HttpURLConnection =url1.openConnection() as HttpURLConnection
                conn1.setUseCaches(false)
                conn1.setRequestMethod("POST")
                conn1.setRequestProperty("Content-Type", "application/json")
                conn1.setRequestProperty("Connection","keep-alive")
                conn1.setRequestProperty("Accept", "application/json")
                conn1.setDoOutput(true)
                conn1.setDoInput(true)

                var requestBody1 = bookMark

                val wr1 = DataOutputStream(conn1.getOutputStream())
                wr1.writeBytes(requestBody1)
                wr1.flush()
                wr1.close()

                val streamReader12 = InputStreamReader(conn1.inputStream)
                val buffered1 = BufferedReader(streamReader12)
                val content1 = StringBuilder()
                while(true) {
                    val line1 = buffered1.readLine() ?: break
                    content1.append(line1)
                }
                val data1 =content1.toString()
                val jsonArr1 = JSONArray(data1)
                for (i in 0 until jsonArr1.length()) {
                    val jsonObj1 = jsonArr1.getJSONObject(i)
                    val recipeStep1 = ArrayList<RecipeProcess>()
                    val recipeIngre1 = ArrayList<IngredientsInfo>()
                    val reciperecipeIngredientsTag1 = ArrayList<String>()

                    val recipeItemArray1 = jsonArr1.getJSONObject(i).getJSONObject("recipe").getJSONArray("recipe_item")
                    Log.d("Response",recipeItemArray1.toString())
                    for(j in 0 until recipeItemArray1.length()){
                        recipeStep1.add(
                            RecipeProcess(
                                recipeItemArray1.getJSONObject(j).getString("txt"),
                                URL(recipeItemArray1.getJSONObject(j).getString("img"))
                            )
                        )
                    }

                    val ingreArray1 =jsonArr1.getJSONObject(i).getJSONObject("ingre").getJSONArray("ingre_item")
                    for(j in 0 until ingreArray1.length()){
                        recipeIngre1.add(
                            IngredientsInfo(
                                ingreArray1.getJSONObject(j).getString("ingre_name"),
                                ingreArray1.getJSONObject(j).getString("ingre_count").toDoubleOrNull(),
                                ingreArray1.getJSONObject(j).getString("ingre_unit"))
                        )
                    }

                    val ingreSearchArray1 = jsonArr1.getJSONObject(i).getJSONArray("ingre_search")
                    for(j in 0 until ingreSearchArray1.length()){
                        reciperecipeIngredientsTag1.add(ingreSearchArray1.getString(j).toString())
                    }

                    recipeTestList.add(
                        RecipeItem(
                            jsonObj1.getString("id"),
                            jsonObj1.getString("name"),
                            recipeIngre1,
                            jsonObj1.getString("summary"),
                            jsonObj1.getDouble("rate_sum")/jsonObj1.getDouble("rate_num"),
                            jsonObj1.getString("time"),
                            jsonObj1.getString("difficult"),
                            jsonObj1.getString("author"),
                            URL(jsonObj1.getString("img")),
                            recipeStep1,
                            reciperecipeIngredientsTag1,
                            jsonObj1.getString("serving")
                        ))
                }

                buffered1.close()
                conn1.disconnect()

                handler.post{
                    binding.recyclerviewBookmarkList.adapter = RecipeItemAdapter(recipeTestList)
                }
            }

        }).start()

    }
}