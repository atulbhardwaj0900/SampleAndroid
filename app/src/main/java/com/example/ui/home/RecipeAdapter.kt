package com.example.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.R
import com.example.services.model.Recipe
import kotlinx.android.synthetic.main.row_recipe.view.*

class RecipeAdapter(private val recipeList: List<Recipe>) : RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder>() {
    var onRowClicked: ((Recipe, Int) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        return RecipeViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.row_recipe, parent, false))
    }
    override fun getItemCount(): Int = recipeList.size

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        val item = recipeList[position]
        holder.bindPost(item, position)
    }

    inner class RecipeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val description = itemView.name
        private val thumbNail = itemView.image
        fun bindPost(question: Recipe, position: Int) {
            description.text = question.description
            Glide.with(itemView).load(question.thumb)
                .placeholder(R.drawable.ic_no_picture)
                .error(R.drawable.ic_no_picture).into(thumbNail)
             itemView.setOnClickListener {
                 onRowClicked?.invoke(question, position)
             }
        }
    }
}