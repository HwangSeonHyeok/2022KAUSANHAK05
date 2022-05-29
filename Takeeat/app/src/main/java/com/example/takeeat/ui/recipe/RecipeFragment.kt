package com.example.takeeat.ui.recipe

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.AnimationDrawable
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.*
import android.widget.ImageView
import androidx.appcompat.app.AppCompatDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.amazonaws.mobile.client.AWSMobileClient
import com.example.takeeat.*
import com.example.takeeat.databinding.FragmentRecipeBinding
import com.example.takeeat.ui.refrigerator.RefItem
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import org.json.JSONArray
import org.json.JSONObject
import java.io.BufferedReader
import java.io.DataOutputStream
import java.io.InputStreamReader
import java.lang.Math.min
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
    lateinit var refItemArray : ArrayList<RefItem>

    val refItemTagArray = ArrayList<String>()
    val recommendData = ArrayList<RecipeBlock>() //여기 모델값이 들어가야함

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecipeBinding.inflate(inflater, container, false)
        val recommendRecyclerView: RecyclerView = binding.recipefragmentMainRecommendView
        val recipeCategoryList: Array<String> = resources.getStringArray(R.array.CategoryTagArray)
        val categoryPager : ViewPager2 = binding.recipefragmentViewPager
        val categoryTabLayout : TabLayout = binding.recipefragmentTabLayout

        val handler = Handler()
        (context as MainActivity).progressON()
        Thread {
            refItemArray = get_ref_item()
            post_recommend_recipe()
            val recipe_list: ArrayList<RecipeItem> =
                get_recommend_recipe()//테스트용 코드 나중에 추천코드로 변경
            var ingreRecipeArray: ArrayList<RecipeItem> = ArrayList<RecipeItem>()
            var recommendIngre : RefItem? = null
            if(refItemArray.size!=0) {
                recommendIngre = refItemArray.minByOrNull { it.itemexp!!}

                if (recommendIngre != null){
                    val rObject = JSONObject()
                    rObject.put(
                        "item_tag",
                        URLEncoder.encode(recommendIngre.itemtag, "UTF-8")
                    )
                    ingreRecipeArray = get_recipe_item(rObject)
                }
            }

            handler.post {

                for (item in refItemArray) {
                    if (item.itemtag != null) {
                        if (!refItemTagArray.contains(item.itemtag))
                            refItemTagArray.add(item.itemtag!!)
                    }
                }

                val categoryAdapter = CategoryPagerAdapter(refItemTagArray, recipeCategoryList)
                categoryPager.adapter = categoryAdapter
                categoryTabLayout.addOnTabSelectedListener(object : OnTabSelectedListener {
                    override fun onTabSelected(tab: TabLayout.Tab) {
                        val position = tab.position
                        val refPage = (categoryPager.adapter as CategoryPagerAdapter).refPage
                        if (position == 0 && categoryPager.currentItem >= refPage) {
                            categoryPager.setCurrentItem(0)
                        } else if (position == 1) {
                            categoryPager.setCurrentItem(refPage)
                        }
                    }

                    override fun onTabUnselected(tab: TabLayout.Tab) {}
                    override fun onTabReselected(tab: TabLayout.Tab) {}
                })
                categoryPager.registerOnPageChangeCallback(object :
                    ViewPager2.OnPageChangeCallback() {
                    override fun onPageScrolled(
                        position: Int,
                        positionOffset: Float,
                        positionOffsetPixels: Int
                    ) {
                        super.onPageScrolled(position, positionOffset, positionOffsetPixels)

                    }

                    override fun onPageSelected(position: Int) {
                        super.onPageSelected(position)
                        val refPage = (categoryPager.adapter as CategoryPagerAdapter).refPage
                        if (position == refPage) {
                            categoryTabLayout.selectTab(categoryTabLayout.getTabAt(1)!!)
                            Log.d("ResponsePageChange", "1" + "position" + position.toString())
                        } else if (position == refPage - 1) {
                            categoryTabLayout.selectTab(categoryTabLayout.getTabAt(0)!!)
                            Log.d("ResponsePageChange", "0" + "position" + position.toString())
                        }

                    }

                    override fun onPageScrollStateChanged(state: Int) {
                        super.onPageScrollStateChanged(state)


                    }
                })

                if(recommendData.size == 0) {
                    var name = AWSMobileClient.getInstance().username.split("@")
                    recommendData.add(
                        RecipeBlock(
                            name[0] + "님을 위한 추천요리",
                            recipe_list
                        )
                    )
                    if(recommendIngre != null) {
                        ingreRecipeArray.sortByDescending{it.recipeRating}
                        val blockitem = ingreRecipeArray.slice(0..min(9,ingreRecipeArray.size)) as ArrayList<RecipeItem>
                        recommendData.add(RecipeBlock("냉장고에 " + recommendIngre.itemname + "로 요리해보세요",blockitem))
                    }
                }

                adapter = RecipeRecommendBlockAdapter(recommendData)
                recommendRecyclerView.adapter = adapter
                for (i in recommendData) {
                    Log.d("Response", "why" + i.recommendList.toString())
                }
                //테스토용 여기까지
                (context as MainActivity).progressOFF()


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
            val searchintent: Intent = Intent(context, RecipeSearchActivity::class.java)
            Log.d("Response",refItemTagArray.toString())
            searchintent.putExtra("ref_Item_Array",refItemTagArray)
            startActivity(searchintent)
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

        try {
            val url:URL = URL("https://b62cvdj81b.execute-api.ap-northeast-2.amazonaws.com/ref-api-test/ref" + "/" + AWSMobileClient.getInstance().username)
            val urlConnection = url.openConnection() as HttpURLConnection
            urlConnection.requestMethod = "GET"

            if (urlConnection.responseCode == HttpURLConnection.HTTP_OK) {
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

                }
                buffered.close()
                urlConnection.disconnect()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }


        return itemTestList
    }

    fun get_recommend_recipe() : ArrayList<RecipeItem>{
        val recipeTestList = ArrayList<RecipeItem>()

        val url: URL = URL("https://b62cvdj81b.execute-api.ap-northeast-2.amazonaws.com/ref-api-test/recommend/get_recommend")
        var conn: HttpURLConnection =url.openConnection() as HttpURLConnection
        conn.setUseCaches(false)
        conn.setRequestMethod("POST")
        conn.setRequestProperty("Content-Type", "application/json")
        conn.setRequestProperty("Connection","keep-alive")
        conn.setRequestProperty("Accept", "application/json")
        conn.setDoOutput(true)
        conn.setDoInput(true)


        var requestBody = "{}"

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

        for (i in 0 until jsonArr.length()) {
            val jsonObj = jsonArr.getJSONObject(i)
            val recipeStep = ArrayList<RecipeProcess>()
            val recipeIngre = ArrayList<IngredientsInfo>()
            val reciperecipeIngredientsTag = ArrayList<String>()
            val recipeItemArray = jsonArr.getJSONObject(i).getJSONObject("recipe").getJSONArray("recipe_item")
            for(j in 0 until recipeItemArray.length()){
                recipeStep.add(
                    RecipeProcess(
                    recipeItemArray.getJSONObject(j).getString("txt"),
                    URL(recipeItemArray.getJSONObject(j).getString("img")))
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

        return recipeTestList
    }

    fun post_recommend_recipe(){
        val url: URL = URL("http://52.78.228.196/recom")
        var conn: HttpURLConnection =url.openConnection() as HttpURLConnection
        conn.setUseCaches(false)
        conn.setRequestMethod("POST")
        conn.setRequestProperty("Content-Type", "application/json")
        conn.setRequestProperty("Connection","keep-alive")
        conn.setRequestProperty("Accept", "application/json")
        conn.setDoOutput(true)
        conn.setDoInput(true)

        var requestBody = "{\"user_name\":\"" + AWSMobileClient.getInstance().username + "\"}"

        val wr = DataOutputStream(conn.getOutputStream())
        wr.writeBytes(requestBody)
        wr.flush()
        wr.close()

        conn.disconnect()
    }

    fun get_recipe_item(job : JSONObject) : java.util.ArrayList<RecipeItem> {
        val recipeTestList = java.util.ArrayList<RecipeItem>()

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
        for (i in 0 until jsonArr.length()) {
            val jsonObj = jsonArr.getJSONObject(i)
            val recipeStep = java.util.ArrayList<RecipeProcess>()
            val recipeIngre = java.util.ArrayList<IngredientsInfo>()
            val reciperecipeIngredientsTag = java.util.ArrayList<String>()
            val recipeItemArray = jsonArr.getJSONObject(i).getJSONObject("recipe").getJSONArray("recipe_item")
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
                )
            )
        }
        buffered.close()
        conn.disconnect()

        return recipeTestList
    }

}