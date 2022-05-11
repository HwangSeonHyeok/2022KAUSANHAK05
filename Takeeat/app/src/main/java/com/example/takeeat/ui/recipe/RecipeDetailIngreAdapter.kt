package com.example.takeeat.ui.recipe

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.amazonaws.mobile.client.AWSMobileClient
import com.example.takeeat.AppDatabase
import com.example.takeeat.IngredientsInfo
import com.example.takeeat.R
import com.example.takeeat.ShoppingListItem
import java.lang.Math.round
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

class RecipeDetailIngreAdapter(var data: ArrayList<IngredientsInfo>):  RecyclerView.Adapter<RecipeDetailIngreAdapter.ViewHolder>() {
    lateinit var db : AppDatabase

    var inMyRef : ArrayList<RefItem> = ArrayList<RefItem>()
    //public var recipeMyRefIngreList : ArrayList<RefItem>? = null

    //lateinit var inMyRef : ArrayList<RefItem>
    public var recipeID = "1"
    public var recipeMyRefIngreList : ArrayList<RefItem>? = null

    var check : Int = 0

    inner class ViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_recipeingre, parent, false)) {
        val ingreName: TextView
        val ingreCount: TextView
        val ingreInMyRef: TextView
        val addShoppingListButton : ImageView

        init {
            db = Room.databaseBuilder(parent.context,AppDatabase::class.java,"shoppinglist").allowMainThreadQueries().build()
            ingreName = itemView.findViewById(R.id.itemrecipeingre_name)
            ingreCount = itemView.findViewById(R.id.itemrecipeingre_amount)
            ingreInMyRef = itemView.findViewById(R.id.itemrecipeingre_refAmount)
            addShoppingListButton = itemView.findViewById(R.id.itemrecipeingre_addCart)
        }

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.d("Responsee : ingreAdapter inmyRef : ",inMyRef.toString())
        data.get(position).let { item ->
            with(holder) {
                ingreName.text = item.ingreName

                if(item.ingreCount!=null)
                    ingreCount.text = (round(item.ingreCount * 10) /10f).toString()
                else
                    ingreCount.text = item.ingreUnit
                ingreInMyRef.text = "0개" // user냉장고 안에 ingreName 태그이 일치하는 품목이 있는지 확인하고 그 수의 합을 여기 저장
                if(inMyRef!=null) {
                    for (i in 0 until inMyRef!!.size) {
                        if (ingreName.text.contains(inMyRef!!.get(i).itemtag!!)) {
                            ingreInMyRef.text =
                                inMyRef!!.get(i).itemamount.toString() + inMyRef!!.get(i).itemunit

                            if (inMyRef != null) {
                                for (i in 0 until inMyRef!!.size) {
                                    if (ingreName.text.contains(inMyRef!!.get(i).itemtag!!)) {
                                        ingreInMyRef.text =
                                            inMyRef!!.get(i).itemamount.toString() + inMyRef!!.get(i).itemunit
                                        //inMyRef!!.add(recipeMyRefIngreList!!.get(i).itemid!!.toInt())
                                        check = 1
                                    }

                                }
                            }
                            if (check != 1) {
                                //inMyRef!!.add(0)
                            }
                            check = 0
                            addShoppingListButton.setOnClickListener {
                                var dbitem: ShoppingListItem? = null
                                var toastvalue: String = item.ingreName
                                if (ingreCount != null)
                                    dbitem = ShoppingListItem(
                                        System.currentTimeMillis(),
                                        item.ingreName + " " + item.ingreCount.toString() + item.ingreUnit,
                                        1
                                    )
                                else
                                    dbitem = ShoppingListItem(
                                        System.currentTimeMillis(),
                                        item.ingreName,
                                        1
                                    )
                                val lastchar = item.ingreName[item.ingreName.length - 1]
                                db.itemDao().insertItem(dbitem)
                                if (lastchar.toInt() >= 0xAC00 && lastchar.toInt() <= 0xD7A3) {
                                    if ((lastchar.toInt() - 0xAC00) % 28 > 0)
                                        toastvalue += "을"
                                    else
                                        toastvalue += "를"
                                }
                                Toast.makeText(
                                    holder.addShoppingListButton.context,
                                    toastvalue + " 쇼핑리스트에 추가되었습니다",
                                    Toast.LENGTH_SHORT
                                ).show();
                            }

                        }
                    }
                }
            }
        }
    }


    override fun getItemCount(): Int {
        return data.size
    }

}