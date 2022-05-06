package com.example.takeeat.ui.recipe

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.takeeat.R
import com.example.takeeat.ShoppingListActivity
import com.example.takeeat.databinding.FragmentRecipeBinding

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
        menu.removeItem(R.id.cart_button)
        menu.removeItem(R.id.notification_button)
        inflater.inflate(R.menu.search_menu, menu)

        val searchButton = menu.findItem(R.id.app_bar_search_refrigerator)
        searchButton.isVisible = false

        menu.findItem(R.id.cart_button).setOnMenuItemClickListener(MenuItem.OnMenuItemClickListener {

            val shoppingintent: Intent = Intent(context, ShoppingListActivity::class.java)
            startActivity(shoppingintent)
            true
        })

        menu.findItem(R.id.app_bar_search_recipe).setOnMenuItemClickListener(MenuItem.OnMenuItemClickListener {

            val searchintent: Intent = Intent(context, RecipeSearchActivity::class.java)
            startActivity(searchintent)
            true
        })

        return super.onCreateOptionsMenu(menu,inflater)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}