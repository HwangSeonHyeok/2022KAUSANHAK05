package com.example.takeeat.ui.myrecipe

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Button
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil.setContentView
import androidx.fragment.app.Fragment
import com.amazonaws.mobile.client.AWSMobileClient
import com.example.takeeat.*
import com.example.takeeat.databinding.FragmentMyrecipeBinding
import com.example.takeeat.databinding.FragmentbookmarkBinding
import com.example.takeeat.databinding.FragmentCreatedBinding
import io.reactivex.disposables.CompositeDisposable
import org.json.JSONArray
import java.io.BufferedReader
import java.io.DataOutputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class CreatedFragment :Fragment() {
    private lateinit var binding: FragmentCreatedBinding
    var myRecipe : ArrayList<RecipeItem> = ArrayList<RecipeItem>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCreatedBinding.inflate(inflater, container, false)
        binding.recipeUploadButton.setOnClickListener{
            val intent= Intent (this@CreatedFragment.requireContext(), RecipeUploadActivity::class.java)
            startActivity(intent)
        }
        setRecipeListRecyclerView()
       /* val uploadbutton= view?.findViewById<Button>(R.id.recipe_upload_button)
        uploadbutton?.setOnClickListener(({
            val intent= Intent(context, RecipeUpload::class.java)
            startActivity(intent)
            activity?.finish()
        }))
*/


        return binding.root
    }

    private fun setRecipeListRecyclerView() {
        // RecipeItemAdapter에 현재 테스트데이터
        // 이 주석친 부분에서 서버 통신을 하시고 더미데이터 자리에 데이터 삽입
        get_Myrecipe()

        binding.recyclerviewRecipeList.adapter = RecipeItemAdapter(myRecipe)

    }

    fun get_Myrecipe(){
        val handler = Handler()
        Thread(Runnable{

        val recipeTestList = ArrayList<RecipeItem>()

        val url: URL = URL("https://b62cvdj81b.execute-api.ap-northeast-2.amazonaws.com/ref-api-test/recipe/getmyrecipe")
        var conn: HttpURLConnection =url.openConnection() as HttpURLConnection
        conn.setUseCaches(false)
        conn.setRequestMethod("POST")
        conn.setRequestProperty("Content-Type", "application/json")
        conn.setRequestProperty("Connection","keep-alive")
        conn.setRequestProperty("Accept", "application/json")
        conn.setDoOutput(true)
        conn.setDoInput(true)

        var requestBody = "{\"id\": \"" + AWSMobileClient.getInstance().username + "\"}"

        val wr = DataOutputStream(conn.getOutputStream())
        wr.writeBytes(requestBody)
        wr.flush()
        wr.close()

        val streamReader = InputStreamReader(conn.inputStream)
        val buffered = BufferedReader(streamReader)

        val content = StringBuilder()
        while(true) {
            val line = buffered.readLine() ?: break
            content.append(line)
        }
        val data =content.toString()
        val jsonArr = JSONArray(data)
        val i = 0
        for (i in 0 until jsonArr.length()) {
            val jsonObj = jsonArr.getJSONObject(i)
            val recipeStep = ArrayList<RecipeProcess>()
            val recipeIngre = ArrayList<IngredientsInfo>()
            val reciperecipeIngredientsTag = ArrayList<String>()

            val recipeItemArray = jsonArr.getJSONObject(i).getJSONObject("recipe").getJSONArray("recipe_item")
            Log.d("Response",recipeItemArray.toString())
            for(j in 0 until recipeItemArray.length()){
                recipeStep.add(
                    RecipeProcess(
                        recipeItemArray.getJSONObject(j).getString("txt"),
                        URL(recipeItemArray.getJSONObject(j).getString("img"))
                    )
                )
            }

            val ingreArray =jsonArr.getJSONObject(i).getJSONObject("ingre").getJSONArray("ingre_item")
            for(j in 0 until ingreArray.length()){
                recipeIngre.add(
                    IngredientsInfo(
                        ingreArray.getJSONObject(j).getString("ingre_name"),
                        ingreArray.getJSONObject(j).getString("ingre_count").toDoubleOrNull(),
                        ingreArray.getJSONObject(j).getString("ingre_unit"))
                )
            }

            val ingreSearchArray = jsonArr.getJSONObject(i).getJSONArray("ingre_search")
            for(j in 0 until ingreSearchArray.length()){
                reciperecipeIngredientsTag.add(ingreSearchArray.getString(j).toString())
            }

            recipeTestList.add(
                RecipeItem(
                    jsonObj.getString("id"),
                    jsonObj.getString("name"),
                    recipeIngre,
                    jsonObj.getString("summary"),
                    jsonObj.getDouble("rate_sum")/jsonObj.getDouble("rate_num"),
                    jsonObj.getString("time"),
                    jsonObj.getString("difficult"),
                    jsonObj.getString("author"),
                    URL(jsonObj.getString("img")),
                    recipeStep,
                    reciperecipeIngredientsTag,
                    jsonObj.getString("serving")
                ))
        }

        buffered.close()
        conn.disconnect()

        myRecipe = recipeTestList
        handler.post{
            binding.recyclerviewRecipeList.adapter = RecipeItemAdapter(recipeTestList)
        }

        }).start()
    }


    companion object {
        val dummyRecipeList:ArrayList<RecipeItem> = arrayListOf(
            RecipeItem(
                "1",
                "베이컨 덮밥",
                arrayListOf(
                    IngredientsInfo("양파", 1.0, "개"),
                    IngredientsInfo("베이컨", 1.0, "개")
                ),
                "집에서 만들기 쉬운 베이컨 덮밥",
                4.5,
                "20분 이내",
                "쉬움",
                "코카콜라",
                URL("https://blog.kakaocdn.net/dn/cWVQdm/btqI5R0K9Xe/srjEm451TL2YL7g4gzwqk0/img.jpg"),
                arrayListOf(
                    RecipeProcess("베이컨 꺼내기", URL("https://blog.kakaocdn.net/dn/cWVQdm/btqI5R0K9Xe/srjEm451TL2YL7g4gzwqk0/img.jpg")),
                    RecipeProcess("양파 썰기", URL("https://blog.kakaocdn.net/dn/cWVQdm/btqI5R0K9Xe/srjEm451TL2YL7g4gzwqk0/img.jpg"))
                ),
                arrayListOf("111", "222"),
                "1인분"
            ),
            RecipeItem(
                "2",
                "떡볶이",
                arrayListOf(
                    IngredientsInfo("떡", 500.0, "g"),
                    IngredientsInfo("고추장", 150.0, "g"),
                    IngredientsInfo("양파", 0.5, "개"),
                    IngredientsInfo("대피", 0.5, "개"),
                    IngredientsInfo("고춧가루", 20.0, "g"),
                    IngredientsInfo("물엿", 20.0, "g")
                ),
                "집에서 만드는 옛날 떡볶이",
                4.5,
                "30분 이내",
                "중간",
                "코카콜라",
                URL("https://t4.daumcdn.net/thumb/R720x0/?fname=http://t1.daumcdn.net/brunch/service/user/55oP/image/a5_8lu1rRhvhlOnk4LRqpBNkuUo.jpg"),
                arrayListOf(
                    RecipeProcess("떡 불리기", URL("https://t4.daumcdn.net/thumb/R720x0/?fname=http://t1.daumcdn.net/brunch/service/user/55oP/image/a5_8lu1rRhvhlOnk4LRqpBNkuUo.jpg")),
                    RecipeProcess("재료 넣고 완성하기", URL("https://t4.daumcdn.net/thumb/R720x0/?fname=http://t1.daumcdn.net/brunch/service/user/55oP/image/a5_8lu1rRhvhlOnk4LRqpBNkuUo.jpg"))
                ),
                arrayListOf("111", "222"),
                "2인분"
            )
        )
    }


}

//val recipeId:String,
//val recipeName:String,
//val recipeIngredients :ArrayList<IngredientsInfo>,
//val recipeSummary:String,
//val recipeRating:Double,
//val recipeTime:String,
//val recipeDifficulty:String,
//val recipeWriter:String?,
//val imgURL: URL?,
//val recipeStep : ArrayList<RecipeProcess>?,
//val recipeIngredientsSearch: ArrayList<String>,
//val recipeServing: String


