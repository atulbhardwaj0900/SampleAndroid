package com.example.ui.home

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.R
import com.example.app.BaseFragment
import kotlinx.android.synthetic.main.home_fragment.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : BaseFragment() {
    private val viewModel: HomeViewModel by viewModel()
    private lateinit var recipeAdapter: RecipeAdapter

    companion object {
        fun newInstance() = HomeFragment()
    }

    override fun getLayoutId(): Int = R.layout.home_fragment

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        getRecipeList()
    }

    private fun getRecipeList() {

        showProgress()
        viewModel.getRecipes().observe(viewLifecycleOwner, { event ->
            popProgress()
            event?.let { response ->
                response.error?.let {
                    showSnackBar(getString(R.string.error_network))
                }
                response.result?.let {
                    recyclerRecipe.layoutManager = LinearLayoutManager(context)
                    recipeAdapter = RecipeAdapter(it)
                    recyclerRecipe.adapter = recipeAdapter
                    recipeAdapter.onRowClicked = { recipe, _ ->
                        showToast(recipe.headline)
                    }
                }
            }
        })
    }
}