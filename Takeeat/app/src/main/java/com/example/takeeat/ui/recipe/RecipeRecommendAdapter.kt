package com.example.takeeat.ui.recipe

import android.content.Intent
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.amazonaws.mobile.client.AWSMobileClient
import com.bumptech.glide.Glide
import com.example.takeeat.R
import com.example.takeeat.RecipeItem
import com.example.takeeat.ui.refrigerator.RefItem
import org.json.JSONArray
import org.json.JSONObject
import java.io.BufferedReader
import java.io.DataOutputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.util.*
import kotlin.collections.ArrayList

class RecipeRecommendAdapter (var data: ArrayList<RecipeItem>):  RecyclerView.Adapter<RecipeRecommendAdapter.ViewHolder>() {


    inner class ViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(
            R.layout.item_recommendrecipe, parent, false
        )
    ), View.OnClickListener {
        val recipeName: TextView
        val recipeImage: ImageView
        override fun onClick(view: View?) {
            val handler = Handler()
            var inMyRef : java.util.ArrayList<RefItem>
            Thread(Runnable {
                inMyRef = get_ingre_myref(data[this.absoluteAdapterPosition].recipeId)
                handler.post {
                    Log.d("Response", "inMyRef:"+inMyRef.toString())
                    val intent = Intent(view!!.context, RecipeDetailActivity::class.java)
                    val recipeData = data[this.absoluteAdapterPosition]
                    recipeData.recipeId
                    intent.putExtra("InMyRef", inMyRef)
                    intent.putExtra("Recipe_Data", recipeData)
                    view.context.startActivity(intent)
                }
            }).start()
        }

        init {
            recipeName = itemView.findViewById(R.id.recommendItemTitle)
            recipeImage = itemView.findViewById(R.id.recommendItemImage)
            itemView.setOnClickListener {onClick(itemView)}
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        data.get(position).let { item ->
            with(holder) {
                recipeName.text = item.recipeName
                Glide.with(holder.recipeImage.context).load(item.imgURL).into(recipeImage)
            }
        }
    }

    override fun getItemCount(): Int {
        Log.d("Response","getItemCount:"+data.size.toString())
        return data.size
    }

    fun get_ingre_myref(recipeId : String) : java.util.ArrayList<RefItem> {


        val recipeMyIngreList = java.util.ArrayList<RefItem>()

        val url: URL = URL("https://b62cvdj81b.execute-api.ap-northeast-2.amazonaws.com/ref-api-test//recipe/isingreinmyref")
        var conn: HttpURLConnection =url.openConnection() as HttpURLConnection
        conn.setUseCaches(false)
        conn.setRequestMethod("POST")
        conn.setRequestProperty("Content-Type", "application/json")
        conn.setRequestProperty("Connection","keep-alive")
        conn.setRequestProperty("Accept", "application/json")
        conn.setDoOutput(true)
        conn.setDoInput(true)

        var job = JSONObject()
        job.put("user_id", AWSMobileClient.getInstance().username)
        job.put("recipe_id", recipeId)
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
        Log.d("Response","get_ingre_myRef"+jsonArr.toString())
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
            recipeMyIngreList.add(
                RefItem(
                    jsonObj.getString("item_name"),
                    tag,
                    date,
                    jsonObj.getString("item_amount").toInt(),
                    jsonObj.getString("item_unit"),
                    jsonObj.getString("item_id"))
            )
            Log.d("Response : jsonObj",jsonObj.toString())

        }
        buffered.close()
        conn.disconnect()


        return recipeMyIngreList



    }

}