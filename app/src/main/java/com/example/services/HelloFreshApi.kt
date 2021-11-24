package com.example.services

import com.example.services.model.Recipe
import retrofit2.Response
import retrofit2.http.GET

interface HelloFreshApi {
    @GET("android-test/recipes.json")
    suspend fun getRecipes(): Response<List<Recipe>>
}