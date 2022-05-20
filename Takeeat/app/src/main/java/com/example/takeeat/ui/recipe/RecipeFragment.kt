package com.example.takeeat.ui.recipe

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.*
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.amazonaws.mobile.client.AWSMobileClient
import com.example.takeeat.*
import com.example.takeeat.databinding.FragmentRecipeBinding
import com.example.takeeat.ui.refrigerator.RefItem
import org.json.JSONArray
import org.json.JSONObject
import java.io.BufferedReader
import java.io.DataOutputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder
import java.util.*
import kotlin.collections.ArrayList

class RecipeFragment : Fragment() {

    private var _binding: FragmentRecipeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    //lateinit var viewModel :RecipeRecommendViewModel
    lateinit var adapter: RecipeRecommendBlockAdapter
    val recommendData = ArrayList<RecipeBlock>() //여기 모델값이 들어가야함

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecipeBinding.inflate(inflater, container, false)
        //viewModel = ViewModelProvider(this).get(RecipeRecommendViewModel::class.java)//여기 모델값이 들어가야함
        val recommendRecyclerView: RecyclerView = binding.recipefragmentMainRecommendView
        //여기부터 표시한곳까진 나중에 지우기 테스트용
        val rObject = JSONObject()
        val handler = Handler()


        rObject.put("item_tag", URLEncoder.encode("고추장", "UTF-8"))
        Thread {
            val recipe_list: ArrayList<RecipeItem> = get_recipe_item(rObject)
            handler.post{
                val blockName = "추천테스트용"
                val recipeArray = ArrayList<RecipeItem>()
                val recipeArray2 = ArrayList<RecipeItem>()
                for(i in 0 until recipe_list.size){
                    if(i<5){
                        recipeArray.add(recipe_list[i])
                        if(recipeArray.size==5){
                            recommendData.add(RecipeBlock("추천테스트"+recommendData.size.toString(),recipeArray))
                        }
                    }
                    if(4<=i){
                        recipeArray2.add(recipe_list[i])
                        if(recipeArray.size==5){
                            recommendData.add(RecipeBlock("추천테스트"+recommendData.size.toString(),recipeArray2))
                        }
                    }

                }
                //recipeArray.clear()
                adapter = RecipeRecommendBlockAdapter(recommendData)
                recommendRecyclerView.adapter = adapter
                for(i in recommendData) {
                    Log.d("Response", "why" + i.recommendList.toString())
                }


            }

        }.start()

        //


        val root: View = binding.root

        /*adapter = RecipeRecommendBlockAdapter(viewModel.data)
        recommendRecyclerView.adapter = adapter*/


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

        val searchButtonRefigerator = menu.findItem(R.id.app_bar_search_refrigerator)
        val searchButtonMyrecipe = menu.findItem(R.id.app_bar_search_myrecipe)
        searchButtonRefigerator.isVisible = false
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

        menu.findItem(R.id.app_bar_search_recipe).setOnMenuItemClickListener(MenuItem.OnMenuItemClickListener {
            //여기에 냉장고 품목 목록 가져오기
            var refItemArray : ArrayList<RefItem>
            val handler = Handler()
            Thread(Runnable{
            //여기 냉장고 가져오는 코드
                refItemArray=get_ref_item()
                Log.d("Responsee : refItem : ",refItemArray.toString())
                handler.post {
                    val refItemTagArray = ArrayList<String>()
                    for(item in refItemArray){
                        if(item.itemtag!=null) {
                            if(!refItemTagArray.contains(item.itemtag))
                                refItemTagArray.add(item.itemtag!!)
                        }
                    }
                    //val
                    //for(tag in refItemTagArray)


                    val searchintent: Intent = Intent(context, RecipeSearchActivity::class.java)


                    Log.d("Response",refItemTagArray.toString())
                    searchintent.putExtra("ref_Item_Array",refItemTagArray)
                    startActivity(searchintent)
              }
            }).start()
            true
        })

        return super.onCreateOptionsMenu(menu,inflater)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun get_ref_item(): ArrayList<RefItem> {
        val itemTestList = ArrayList<RefItem>()

        // 네트워킹 예외처리를 위한 try ~ catch 문
        try {
            val url:URL = URL("https://b62cvdj81b.execute-api.ap-northeast-2.amazonaws.com/ref-api-test/ref" + "/" + AWSMobileClient.getInstance().username)

            // 서버와의 연결 생성
            val urlConnection = url.openConnection() as HttpURLConnection
            urlConnection.requestMethod = "GET"

            if (urlConnection.responseCode == HttpURLConnection.HTTP_OK) {
                // 데이터 읽기
                val streamReader = InputStreamReader(urlConnection.inputStream)
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
                    val datestr: String = jsonObj.getString("item_exdate")
                    var date: Date? = null
                    var tag : String? = null
                    if(datestr != "NULL"){
                        val numm = datestr.split("-")
                        date = Date(numm[0].toInt(),numm[1].toInt()-1,numm[2].toInt())
                    }
                    if(jsonObj.getString("item_tag")=="NULL"){
                        tag = "기타"
                    }else{
                        tag  = jsonObj.getString("item_tag")
                    }
                    itemTestList.add(RefItem(
                        jsonObj.getString("item_name"),
                        tag,
                        date,
                        jsonObj.getString("item_amount").toInt(),
                        jsonObj.getString("item_unit"),
                        jsonObj.getString("item_id")))
                    Log.d("Response : jsonObj",jsonObj.toString())

                }

                // 스트림과 커넥션 해제
                buffered.close()
                urlConnection.disconnect()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }


        return itemTestList
    }

    fun get_recipe_item(job : JSONObject) : ArrayList<RecipeItem>{
        //Thread(Runnable{
        //handler.post{
        //try {
        AWSMobileClient.getInstance()
        val recipeTestList = ArrayList<RecipeItem>()

        val url: URL = URL("https://b62cvdj81b.execute-api.ap-northeast-2.amazonaws.com/ref-api-test/ref/item_get_recipe")
        var conn: HttpURLConnection =url.openConnection() as HttpURLConnection
        conn.setUseCaches(false)
        conn.setRequestMethod("POST")
        conn.setRequestProperty("Content-Type", "application/json")
        conn.setRequestProperty("Connection","keep-alive")
        conn.setRequestProperty("Accept", "application/json")
        conn.setDoOutput(true)
        conn.setDoInput(true)


        var requestBody = job.toString()


        Log.d("Response1 = ",requestBody)
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
        //Log.d("Response : jsonArr",jsonArr.getJSONObject(0).getJSONArray("recipe").toString())
        //Log.d("Response : jsonlength",jsonArr.length().toString())
        val i = 0
        for (i in 0 until jsonArr.length()) {
            val jsonObj = jsonArr.getJSONObject(i)
            val recipeStep = ArrayList<RecipeProcess>()
            val recipeIngre = ArrayList<IngredientsInfo>()
            val reciperecipeIngredientsTag = ArrayList<String>()

            Log.d("Response : recipe", "들어옴")
            val recipeItemArray = jsonArr.getJSONObject(i).getJSONObject("recipe").getJSONArray("recipe_item")
            for(j in 0 until recipeItemArray.length()){
                recipeStep.add(
                    RecipeProcess(
                    recipeItemArray.getJSONObject(j).getString("txt"),
                    URL(recipeItemArray.getJSONObject(j).getString("img")))
                )
            }
            Log.d("Response : recipe", "나감")

            //Log.d("Response : ingre", jsonArr.getJSONObject(i).getJSONObject("ingre").getJSONArray("ingre_item").length().toString())
            val ingreArray =jsonArr.getJSONObject(i).getJSONObject("ingre").getJSONArray("ingre_item")
            Log.d("Response : ingre", "들어옴")
            for(j in 0 until ingreArray.length()){
                recipeIngre.add(
                    IngredientsInfo(
                    ingreArray.getJSONObject(j).getString("ingre_name"),
                    ingreArray.getJSONObject(j).getString("ingre_count").toDoubleOrNull(),
                    ingreArray.getJSONObject(j).getString("ingre_unit"))
                )
            }
            Log.d("Response : ingre", recipeIngre.toString())

            Log.d("Response : scc", jsonArr.getJSONObject(i).getJSONArray("ingre_search").toString())
            //Log.d("Response : scc", jsonArr.getJSONObject(i).getJSONArray("ingre_search").getString(1).toString())

            val ingreSearchArray = jsonArr.getJSONObject(i).getJSONArray("ingre_search")
            for(j in 0 until ingreSearchArray.length()){
                reciperecipeIngredientsTag.add(ingreSearchArray.getString(j).toString())
            }







            /*
            for(j in 0 until jsonArr.getJSONObject(i).getJSONArray("recipe").length()){
                recipeStep.add(RecipeProcess(jsonArr.getJSONObject(i).getJSONArray("recipe").getJSONObject(j).getString("txt"),URL(jsonArr.getJSONObject(i).getJSONArray("recipe").getJSONObject(j).getString("img"))))
            }
            for(j in 0 until jsonArr.getJSONObject(i).getJSONArray("ingre").length()){
                recipeIngre.add(IngredientsInfo(jsonArr.getJSONObject(i).getJSONArray("ingre").getJSONObject(j).getString("ingre_name"),jsonArr.getJSONObject(i).getJSONArray("ingre").getJSONObject(j).getString("ingre_count").toDoubleOrNull(),jsonArr.getJSONObject(i).getJSONArray("ingre").getJSONObject(j).getString("ingre_unit")))
            }

             */




            /*


             */
            Log.d("Response : recipeStep", "RecipeStep"+recipeStep.toString())
            Log.d("Response : jsonObj",jsonObj.toString())
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
            Log.d("Response : ingreeee",recipeIngre.toString())
            Log.d("Response : ingreeee",reciperecipeIngredientsTag.toString())
        }

        // 스트림과 커넥션 해제
        buffered.close()
        conn.disconnect()






        //}).start()
        return recipeTestList
    }
}