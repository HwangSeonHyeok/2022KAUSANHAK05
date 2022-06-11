package com.example.takeeat.ui.recipe

import android.R
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.AnimationDrawable
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDialog
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.amazonaws.mobile.client.AWSMobileClient
import com.example.takeeat.IngredientsInfo
import com.example.takeeat.ShoppingListAdapter
import com.example.takeeat.ShoppingListItem
import com.example.takeeat.ShoppingListViewModel
import com.example.takeeat.databinding.ActivitySubtractrefBinding
import com.example.takeeat.ui.refrigerator.RefItem
import org.json.JSONObject
import java.io.BufferedReader
import java.io.DataOutputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder

class SubtractRefActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySubtractrefBinding
    lateinit var ingreList: ArrayList<IngredientsInfo>
    lateinit var inMyRef: ArrayList<RefItem>
    lateinit var subtractRefAdapter: SubtractRefAdapter
    lateinit var viewModel: SubtractRefViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySubtractrefBinding.inflate(layoutInflater)
        ingreList = intent.getSerializableExtra("Ingre_Data") as ArrayList<IngredientsInfo>
        inMyRef = intent.getSerializableExtra("inMyRef") as ArrayList<RefItem>
        viewModel = ViewModelProviders.of(this).get(SubtractRefViewModel::class.java)
        if (viewModel.liveData.value == null) {
            for (ingre in ingreList) {
                val refItemSameTag = ArrayList<RefItem>()
                if (ingre.ingreCount != null) {
                    for (refItem in inMyRef) {
                        if (ingre.ingreName.contains(refItem.itemtag!!)) {
                            refItemSameTag.add(refItem)
                        }
                    }
                    if (refItemSameTag.size > 0) {
                        /*val spinnerUnitItem = ArrayList<String>()
                    for (i in refItemSameTag) {
                        val itemexp = i.itemexp!!
                        spinnerUnitItem.add(i.itemname!! + " 유통기한:" + itemexp.year.toString() + "." + (itemexp.month + 1).toString() + "." + itemexp.date.toString())
                    }*/
                        val subRefItem = SubtractRefItem(
                            ingre.ingreName + " " + ingre.ingreCount + ingre.ingreUnit,
                            refItemSameTag,
                            refItemSameTag[0].itemamount!!,
                            refItemSameTag[0]
                        )
                        viewModel.addData(subRefItem)
                    }

                }
            }
        }
        setContentView(binding.root)

        val dataObserver: Observer<ArrayList<SubtractRefItem>> =
            Observer { livedata ->
                subtractRefAdapter = SubtractRefAdapter(viewModel.liveData)
                Log.d("Response", viewModel.liveData.value!!.size.toString())
                binding.subrefRecyclerView.adapter = subtractRefAdapter
            }
        viewModel.liveData.observe(this, dataObserver)

        binding.subrefApplyButton.setOnClickListener {
            val handler = Handler()
            progressON(this)
            Thread {
                for (item in viewModel.liveData.value!!) {
                    Log.d(
                        "Response",
                        "Progress" + item.currentProgress.toString() + "am" + item.selectedItem.itemamount.toString()
                    )
                    if (item.currentProgress == item.selectedItem.itemamount) {
                        Log.d("Reponse", "here?")
                        //delete
                        val sObject = JSONObject() //배열 내에 들어갈 json
                        sObject.put(
                            "id",
                            AWSMobileClient.getInstance().username+"item:" + item.selectedItem.itemid
                        )
                        sObject.put("item_id", item.selectedItem.itemid)

                        delete_ref_item(sObject)
                    } else if (item.currentProgress == 0) {

                    } else {
                        //update
                        val sObject = JSONObject() //배열 내에 들어갈 json
                        sObject.put(
                            "id",AWSMobileClient.getInstance().username + "item:" +item.selectedItem.itemid
                        )
                        sObject.put("item_id", item.selectedItem.itemid)
                        sObject.put(
                            "update_name",
                            URLEncoder.encode(item.selectedItem.itemname, "UTF-8")
                        )
                        sObject.put(
                            "update_amount",
                            item.selectedItem.itemamount!! - item.currentProgress
                        )
                        sObject.put(
                            "update_exdate",
                            item.selectedItem.itemexp!!.year.toString() + "-" + (item.selectedItem.itemexp!!.month + 1).toString() + "-" + item.selectedItem.itemexp!!.date.toString()
                        )//binding.refDetailEXP.text.toString().replace(".", "-"))
                        sObject.put(
                            "update_tag",
                            URLEncoder.encode(item.selectedItem.itemtag, "UTF-8")
                        )
                        sObject.put(
                            "update_unit",
                            URLEncoder.encode(item.selectedItem.itemunit, "UTF-8")
                        )


                        update_ref_item(sObject)
                    }
                }
                handler.post {
                    progressOFF()
                    //Toast.makeText(this, "삭제되었습니다", Toast.LENGTH_LONG).show()
                    finish()
                }
            }.start()
            Log.d("Response", viewModel.liveData.value.toString())

        }
    }

    fun update_ref_item(job: JSONObject) {

        val url: URL = URL("https://b62cvdj81b.execute-api.ap-northeast-2.amazonaws.com/ref-api-test/ref/update")
        var conn: HttpURLConnection = url.openConnection() as HttpURLConnection
        conn.setUseCaches(false)
        conn.setRequestMethod("POST")
        conn.setRequestProperty("Content-Type", "application/json")
        conn.setRequestProperty("Connection", "keep-alive")
        conn.setRequestProperty("Accept", "application/json")
        conn.setDoOutput(true)
        conn.setDoInput(true)

        var requestBody = job.toString()

        val wr = DataOutputStream(conn.getOutputStream())
        wr.writeBytes(requestBody)
        wr.flush()
        wr.close()

        Log.d("Response : responseCode", conn.getResponseCode().toString())
        conn.disconnect()

    }


    fun delete_ref_item(job: JSONObject) {

        val url: URL =
            URL("https://b62cvdj81b.execute-api.ap-northeast-2.amazonaws.com/ref-api-test/ref/delete")
        var conn: HttpURLConnection = url.openConnection() as HttpURLConnection
        conn.setUseCaches(false)
        conn.setRequestMethod("POST")
        conn.setRequestProperty("Content-Type", "application/json")
        conn.setRequestProperty("Connection", "keep-alive")
        conn.setRequestProperty("Accept", "application/json")
        conn.setDoOutput(true)
        conn.setDoInput(true)

        var requestBody = job.toString()

        val wr = DataOutputStream(conn.getOutputStream())
        wr.writeBytes(requestBody)
        wr.flush()
        wr.close()

        Log.d("Response : responseCode", conn.getResponseCode().toString())
        conn.disconnect()

    }


    lateinit var progressDialog: AppCompatDialog
    fun progressON(context: Context) {
        if (context == null) {
            return
        }
        progressDialog = AppCompatDialog(context)
        progressDialog.setCancelable(false)
        progressDialog.getWindow()!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
        progressDialog.setContentView(com.example.takeeat.R.layout.dialog_loading);
        progressDialog.show();
        val loadingFrame: ImageView? =
            progressDialog.findViewById(com.example.takeeat.R.id.loadingImage)
        if (loadingFrame != null) {
            val frameAnimation = loadingFrame.background as AnimationDrawable
            loadingFrame.post(Runnable { frameAnimation.start() })

        }

    }

    fun progressOFF() {
        if (progressDialog != null && progressDialog.isShowing) {
            progressDialog.dismiss()
        }
    }
}