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
import com.amazonaws.mobile.client.AWSMobileClient
import com.example.takeeat.R
import com.example.takeeat.ShoppingListActivity
import com.example.takeeat.databinding.FragmentRecipeBinding
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

class RecipeFragment : Fragment() {

    private var _binding: FragmentRecipeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val recipeViewModel =
            ViewModelProvider(this).get(RecipeViewModel::class.java)

        _binding = FragmentRecipeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textRecipe
        recipeViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
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

        menu.findItem(R.id.app_bar_search_recipe).setOnMenuItemClickListener(MenuItem.OnMenuItemClickListener {
            //여기에 냉장고 품목 목록 가져오기
            var refItemArray : ArrayList<RefItem>
            val refItemTagArray = ArrayList<String>()
            val handler = Handler()
            Thread(Runnable{
            //여기 냉장고 가져오는 코드
                refItemArray=get_ref_item()
                Log.d("Responsee : refItem : ",refItemArray.toString())
                handler.post {
                    for(item in refItemArray){
                        if(item.itemtag!=null) {
                            refItemTagArray.add(item.itemtag!!)
                        }
                    }
                    val searchintent: Intent = Intent(context, RecipeSearchActivity::class.java)
                    refItemTagArray.distinct()
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
}