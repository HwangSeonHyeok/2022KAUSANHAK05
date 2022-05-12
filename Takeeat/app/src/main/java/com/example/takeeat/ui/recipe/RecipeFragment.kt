package com.example.takeeat.ui.recipe

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.*
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.takeeat.R
import com.example.takeeat.ShoppingListActivity
import com.example.takeeat.databinding.FragmentRecipeBinding
import com.example.takeeat.ui.refrigerator.RefItem

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
            val refItemArray  = ArrayList<RefItem>()
            val refItemTagArray = ArrayList<String>()
            val handler = Handler()
            //Thread{
            //여기 냉장고 가져오는 코드
            //handler.post {
                for(item in refItemArray){
                    if(item.itemtag!=null) {
                        refItemTagArray.add(item.itemtag!!)
                    }
                }
                val searchintent: Intent = Intent(context, RecipeSearchActivity::class.java)
                searchintent.putExtra("ref_Item_Array",refItemArray)
                startActivity(searchintent)
            //  }
            //}
            true
        })

        return super.onCreateOptionsMenu(menu,inflater)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}