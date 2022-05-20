package com.example.takeeat.ui.recipe

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.amazonaws.mobile.client.AWSMobileClient
import com.example.takeeat.IngredientsInfo
import com.example.takeeat.R
import com.example.takeeat.RecipeItem
import com.example.takeeat.RecipeProcess
import com.example.takeeat.databinding.ActivityRecipesearchBinding
import com.example.takeeat.ui.refrigerator.RefItem
import org.json.JSONArray
import java.io.BufferedReader
import java.io.DataOutputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder

class RecipeSearchActivity : AppCompatActivity() {

    private lateinit var binding : ActivityRecipesearchBinding
    private lateinit var filteredIngreList: MutableList<String>
    private lateinit var filteredCategoryList: MutableList<String>
    private lateinit var recipeSearch : MenuItem
    var searchText : String? = null
    //lateinit
    var ResultList : ArrayList<RecipeItem> = ArrayList<RecipeItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecipesearchBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //val recipeIngreList: Array<String> = resources.getStringArray(R.array.RefrigeratorItemTagArray)
        val recipeIngreList: ArrayList<String> = intent.getSerializableExtra("ref_Item_Array") as ArrayList<String>
        val recipeCategoryList: Array<String> = resources.getStringArray(R.array.CategoryTagArray)
        val difficultyList: Array<String> = resources.getStringArray(R.array.DifficultyArray)
        //아래 recipe R.array.RefrigeratorItemTagArray대신에
        //intent.getSerializableExtra("ref_Item_Array") as ArrayList<String>
        //를 넣어주세요
        filteredIngreList = recipeIngreList.toMutableList()
        filteredCategoryList = recipeCategoryList.toMutableList()

        val ingreRecyclerView: RecyclerView = binding.recipeSearchIngreRecyclerview
        val categoryRecyclerView: RecyclerView = binding.recipeSearchCategoryRecyclerview
        val difficultyRecyclerView: RecyclerView = binding.recipeDifficultyRecyclerview
        ingreRecyclerView.layoutManager = GridLayoutManager(this,5)
        categoryRecyclerView.layoutManager = GridLayoutManager(this,5)
        difficultyRecyclerView.layoutManager = GridLayoutManager(this,5)
        val ingreAdapter = RecipeSearchIngreAdapter(filteredIngreList)
        val categoryAdapter = RecipeSearchCategoryAdapter(filteredCategoryList)
        val difficultyAdapter = RecipeSearchDifficultyAdapter(difficultyList)

        ingreRecyclerView.adapter = ingreAdapter
        categoryRecyclerView.adapter = categoryAdapter
        difficultyRecyclerView.adapter = difficultyAdapter

        binding.recipeSearchApplyButton.setOnClickListener {

            //Log.d("Response","selected ingre:" + ingreAdapter.selectedItems.toList())
            //Log.d("Response","selected category:" + categoryAdapter.selectedItem.toString())
            //Log.d("Response","selected difficulty:" + difficultyAdapter.selectedItem.toString())

            var ingreList = ArrayList<String>()
            var name : String// = job.get("name").toString()
            var ingre_search = "" //= job.get("ingre_search")
            var difficult : String // = job.get("difficult").toString()
            var category : String//  = job.get("category").toString()

            for(item in ingreAdapter.selectedItems){
                ingreList.add(URLEncoder.encode(item, "UTF-8"))
                ingre_search = ingre_search + "\"" + URLEncoder.encode(item, "UTF-8") + "\","
            }

            if(ingre_search!=""){
                ingre_search = ingre_search.substring(0, ingre_search.length-1)
            }

            if(searchText==null){
                name = "NULL"
            }else{
                name = URLEncoder.encode(searchText, "UTF-8")
            }

            if(difficultyAdapter.selectedItem.toString()=="null"){
                difficult = "NULL"
            }else{
                difficult = URLEncoder.encode(difficultyAdapter.selectedItem.toString(), "UTF-8")
            }

            if(categoryAdapter.selectedItem.toString()=="null"){
                category = "NULL"
            }else{
                category = URLEncoder.encode(categoryAdapter.selectedItem.toString(), "UTF-8")
            }

            var requeststr = "{\"name\":\"" + name + "\",\"ingre_search\":[" + ingre_search + "],\"difficult\":\"" + difficult + "\",\"category\":\"" + category + "\"}"


            //Log.d("Response","str:" + requeststr)


            var recipeSearchResult : ArrayList<RecipeItem> = ArrayList<RecipeItem>()

            Thread(Runnable{
                recipeSearchResult = search_recipe_item(requeststr)
                Log.d("Response List : ", recipeSearchResult.toString())
            }).start()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.recipe_search_menu,menu)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        recipeSearch = menu.findItem(R.id.recipe_search)

        val recipeIngreList: ArrayList<String> = intent.getSerializableExtra("ref_Item_Array") as ArrayList<String>
        val recipeCategoryList: Array<String> = resources.getStringArray(R.array.CategoryTagArray)


        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        (recipeSearch.actionView as SearchView).apply {
            //Assumes current activity is the searchable activity
            setQuery("", false)
            isIconified = true

            /*fun filterList(newText:String){
                val newIngreList: MutableList<String> = listOf<String>().toMutableList()
                val newCategoryList: MutableList<String> = listOf<String>().toMutableList()
                val ingreRecyclerView: RecyclerView = binding.recipeSearchIngreRecyclerview
                val categoryRecyclerView: RecyclerView = binding.recipeSearchCategoryRecyclerview
                if(newText != "") {
                    for(x in recipeIngreList){
                        if(x.contains(newText)) newIngreList.add(x)
                    }
                    for(x in recipeCategoryList){
                        if(x.contains(newText)) newCategoryList.add(x)
                    }
                }
                else {
                    for(x in recipeIngreList) newIngreList.add(x)
                    for(x in recipeCategoryList) newCategoryList.add(x)
                }

                if(!newIngreList.isNullOrEmpty()) {
                    filteredIngreList.clear()
                    for(x in newIngreList) filteredIngreList.add(x)
                    //ingreRecyclerView.adapter = RecipeSearchIngreAdapter(filteredIngreList)
                    ingreRecyclerView.adapter?.notifyDataSetChanged()
                }
                if(!newCategoryList.isNullOrEmpty()) {
                    filteredCategoryList.clear()
                    for(x in newCategoryList) filteredCategoryList.add(x)
                    //categoryRecyclerView.adapter = RecipeSearchCategoryAdapter(filteredCategoryList)
                    categoryRecyclerView.adapter?.notifyDataSetChanged()
                }
            }*/

            setIconifiedByDefault(true)
            queryHint = "검색어를 입력하세요"
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String): Boolean {
                    //filterList(query)
                    return false
                }

                override fun onQueryTextChange(newText: String): Boolean {
                    //filterList(newText)
                    searchText=newText
                    return false
                }

            })
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.getItemId()) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun search_recipe_item(str : String) : ArrayList<RecipeItem>{
        //Thread(Runnable{
        //handler.post{
        //try {
        AWSMobileClient.getInstance()
        val recipeTestList = ArrayList<RecipeItem>()

        val url: URL = URL("https://b62cvdj81b.execute-api.ap-northeast-2.amazonaws.com/ref-api-test/recipe/search")
        var conn: HttpURLConnection =url.openConnection() as HttpURLConnection
        conn.setUseCaches(false)
        conn.setRequestMethod("POST")
        conn.setRequestProperty("Content-Type", "application/json")
        conn.setRequestProperty("Connection","keep-alive")
        conn.setRequestProperty("Accept", "application/json")
        conn.setDoOutput(true)
        conn.setDoInput(true)

        var requestBody = str


        //Log.d("Responsee reqB = ",requestBody)
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
        //Log.d("Responsee data = ",data)
        val jsonArr = JSONArray(data)
        val i = 0
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

        // 스트림과 커넥션 해제
        buffered.close()
        conn.disconnect()

        //Log.d("Responsee : recipeTestList ",recipeTestList.toString())
        //ResultList = recipeTestList


        //
        //}).start()
        return recipeTestList
    }

}