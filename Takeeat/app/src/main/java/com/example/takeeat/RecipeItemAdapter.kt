package com.example.takeeat

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.AnimationDrawable
import android.graphics.drawable.ColorDrawable
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatDialog
import androidx.recyclerview.widget.RecyclerView
import com.amazonaws.mobile.client.AWSMobileClient
import com.bumptech.glide.Glide
import com.example.takeeat.ui.recipe.RecipeDetailActivity
import com.example.takeeat.ui.refrigerator.RefItem
import org.json.JSONArray
import org.json.JSONObject
import java.io.BufferedReader
import java.io.DataOutputStream
import java.io.InputStreamReader
import java.lang.Math.round
import java.net.HttpURLConnection
import java.net.URL

import java.util.*


class RecipeItemAdapter(var data: ArrayList<RecipeItem>):  RecyclerView.Adapter<RecipeItemAdapter.ViewHolder>() {

    var visibleItemCount = 5

    inner class ViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_recipe, parent,false)),View.OnClickListener{

        val recipeName: TextView
        val recipeImage : ImageView
        val recipeIngredient: TextView
        val recipeIntroduce : TextView
        val recipeRate : TextView
        val recipeTime : TextView
        val recipeDifficulty : TextView
        val recipeWriter: TextView

        override fun onClick(view: View?) {
            val handler = Handler()
            var inMyRef : ArrayList<RefItem>
            progressON(view!!.context)
            Thread(Runnable {
                inMyRef = get_ingre_myref(data[this.absoluteAdapterPosition].recipeId)
                handler.post {
                    Log.d("Response", "inMyRef:"+inMyRef.toString())
                    val intent = Intent(view!!.context, RecipeDetailActivity::class.java)
                    val recipeData = data[this.absoluteAdapterPosition]
                    recipeData.recipeId
                    intent.putExtra("InMyRef", inMyRef)
                    intent.putExtra("Recipe_Data", recipeData)
                    progressOFF()
                    view.context.startActivity(intent)
                }
            }).start()
        }

        init {
            recipeName = itemView.findViewById(R.id.recipe_item_recipename)
            recipeImage = itemView.findViewById(R.id.recipe_item_Image)
            recipeIngredient = itemView.findViewById(R.id.recipe_item_recipeIngredients)
            recipeIntroduce = itemView.findViewById(R.id.recipe_item_recipeintroduce)
            recipeRate = itemView.findViewById(R.id.recipe_item_rating)
            recipeTime = itemView.findViewById(R.id.recipe_item_time)
            recipeDifficulty = itemView.findViewById(R.id.recipe_item_difficulty)
            recipeWriter = itemView.findViewById(R.id.recipe_item_writer)
            itemView.setOnClickListener {onClick(itemView)}
        }


    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent)
    }




    override fun getItemCount(): Int {
        if(data.size<5) return data.size
        else return visibleItemCount

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.apply {
            /*val JJ : JSONArray = data[position].recipeIngredients
            val content = StringBuilder()

            for (i in 0 until JJ.length()){
                val element : JSONObject = JJ.optJSONObject(i)
                content.append(element.optString("ingre_name").toString() + " ")
            }*/
            var ingredientContent:String =""
            for(i in data[position].recipeIngredients){
                ingredientContent += i.ingreName + " "
            }


            recipeName.text = data[position].recipeName
            Glide.with(holder.recipeDifficulty.context).load(data[position].imgURL).into(recipeImage)
            //recipeImage
            //recipeIngredient.text= data[position].recipeIngredients
            recipeIngredient.text= ingredientContent.toString()
            recipeIntroduce.text = data[position].recipeSummary
            recipeRate.text = (round(data[position].recipeRating*10)/10f).toString()
            recipeTime.text = data[position].recipeTime
            recipeDifficulty.text = data[position].recipeDifficulty
            recipeWriter.text = data[position].recipeWriter
        }

    }
    fun addVisibleItemCount(){
        if(visibleItemCount<data.size) visibleItemCount+=5
        if(visibleItemCount>data.size) visibleItemCount = data.size
    }

    fun resetVisibleItemCount(){
        visibleItemCount = 5

    }

    fun get_ingre_myref(recipeId : String) :ArrayList<RefItem>{


        val recipeMyIngreList = ArrayList<RefItem>()

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