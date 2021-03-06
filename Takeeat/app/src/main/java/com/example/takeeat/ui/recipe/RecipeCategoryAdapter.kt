package com.example.takeeat.ui.recipe

import android.content.Context
import android.content.Intent
import android.content.res.TypedArray
import android.graphics.Color
import android.graphics.drawable.AnimationDrawable
import android.graphics.drawable.ColorDrawable
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatDialog
import androidx.recyclerview.widget.RecyclerView
import com.amazonaws.mobile.client.AWSMobileClient
import com.example.takeeat.IngredientsInfo
import com.example.takeeat.R
import com.example.takeeat.RecipeItem
import com.example.takeeat.RecipeProcess
import org.json.JSONArray
import org.json.JSONObject
import java.io.BufferedReader
import java.io.DataOutputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder

class RecipeCategoryAdapter (val data: ArrayList<String>, val mode : Int) : RecyclerView.Adapter<RecipeCategoryAdapter.IconViewHolder>() {
    //mode 0은 냉장고, mode 1은 카테고리
    lateinit var categoryIconArray : TypedArray
    lateinit var ingreTagArray: Array<String>
    var page:Int = 0
    inner class IconViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener{
        val txtItemName: TextView = itemView.findViewById(R.id.recipe_search_item_name_icon)
        val imgItem: ImageView = itemView.findViewById(R.id.recipe_search_item_image_icon)
        override fun onClick(view: View?) {
            val handler = Handler()
            var recipeArray = ArrayList<RecipeItem>()
            progressON(view!!.context)
            Thread {
                if (mode == 0) {
                    val rObject = JSONObject()
                    rObject.put(
                        "item_tag",
                        URLEncoder.encode(data[absoluteAdapterPosition], "UTF-8")
                    )
                    recipeArray = get_recipe_item(rObject)
                } else if (mode == 1) {
                    var requeststr =
                        "{\"name\":\"NULL\",\"ingre_search\":[],\"difficult\":\"NULL\",\"category\":\"" + URLEncoder.encode(data[absoluteAdapterPosition], "UTF-8") + "\"}"
                    recipeArray = search_recipe_item(requeststr)


                }
                handler.post{
                    val intent = Intent(view!!.context, RecipeSearchResultActivity::class.java)
                    intent.putExtra("Search_Result", recipeArray)
                    view.context.startActivity(intent)
                    progressOFF()
                }
            }.start()
        }
        init{
            itemView.setOnClickListener {onClick(itemView)}
            if(mode == 0) {
                categoryIconArray =
                    itemView.context.resources.obtainTypedArray(R.array.IngreIconArray)
                ingreTagArray =itemView.context.resources.getStringArray(R.array.RefrigeratorItemTagArray)

            }
            else if(mode == 1)
                categoryIconArray = itemView.context.resources.obtainTypedArray(R.array.CategoryIconArray)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeCategoryAdapter.IconViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_recipe_search, parent, false)
        return IconViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RecipeCategoryAdapter.IconViewHolder, position: Int) {
        //Log.d("ResponseCategoryAdapter",data[position])
        data.get(position).let { item ->
            with(holder) {
                txtItemName.text = item
                if(mode==0)
                    imgItem.setImageDrawable(categoryIconArray.getDrawable(ingreTagArray.indexOf(item)))
                else if(mode == 1)
                    imgItem.setImageDrawable(categoryIconArray.getDrawable(absoluteAdapterPosition+page*8))

            }
        }
    }

    override fun getItemCount(): Int {
        return data.size
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

    fun search_recipe_item(str : String) : ArrayList<RecipeItem>{
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

        return recipeTestList
    }

    lateinit var progressDialog : AppCompatDialog
    fun progressON(context: Context){
        if(context == null){
            return
        }
        progressDialog = AppCompatDialog(context)
        progressDialog.setCancelable(false)
        progressDialog.getWindow()!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
        progressDialog.setContentView(R.layout.dialog_loading);
        progressDialog.show();
        val loadingFrame : ImageView? = progressDialog.findViewById(R.id.loadingImage)
        if(loadingFrame != null) {
            val frameAnimation = loadingFrame.background as AnimationDrawable
            loadingFrame.post(Runnable { frameAnimation.start()})

        }

    }
    fun progressOFF(){
        if(progressDialog != null && progressDialog.isShowing){
            progressDialog.dismiss()
        }
    }
}