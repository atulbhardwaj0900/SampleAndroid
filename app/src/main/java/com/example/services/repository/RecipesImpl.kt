package com.hellofresh.task2.services.repository

import com.example.services.HelloFreshApi
import com.example.services.model.Recipe
import com.example.services.repository.RecipesRepository
import com.example.services.repository.Result
import com.example.services.repository.safeApiCall

class RecipesImpl(private val helloFreshApi: HelloFreshApi) :
    RecipesRepository {

    override suspend fun getRecipes(): Result<List<Recipe>> =
        safeApiCall { doGetRecipes() }

    private suspend fun doGetRecipes(): Result<List<Recipe>> {
        val response = helloFreshApi.getRecipes()
        return if (response.isSuccessful)
            Result.Success(response.body()!!)
        else Result.GenericError(response.code(), response.message())
    }
}