package com.example.services.repository

import com.example.services.model.Recipe

interface RecipesRepository {
    suspend fun getRecipes(): Result<List<Recipe>>
}