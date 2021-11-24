package com.example.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.services.ErrorObject
import com.example.services.model.Recipe
import com.example.services.repository.RecipesRepository
import com.example.services.repository.ErrorResponses
import com.example.services.repository.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeViewModel(private val repository: RecipesRepository) : ViewModel() {

    private var recipeLiveData: MutableLiveData<RecipesListWrapper>? = null

    fun getRecipes(): LiveData<RecipesListWrapper> {
        if (recipeLiveData == null) {
            recipeLiveData = MutableLiveData()

            viewModelScope.launch {
                when (val result = withContext(Dispatchers.IO) {
                    repository.getRecipes(
                    )
                }) {
                    is Result.Success -> {
                        recipeLiveData!!.postValue(

                                RecipesListWrapper(
                                    result.data
                            )
                        )
                    }
                    is Result.GenericError -> recipeLiveData!!.postValue(
                            RecipesListWrapper(
                                error = ErrorObject(
                                    result.code,
                                    result.error
                            )
                        )

                    )
                    is Result.NetworkError -> recipeLiveData!!.postValue(
                            RecipesListWrapper(
                                error = ErrorObject(0, ErrorResponses.Network.name)
                        )
                    )
                    is Result.Error -> recipeLiveData!!.postValue(
                            RecipesListWrapper(
                                error = ErrorObject(
                                    0,
                                    result.exception.message
                            )
                        )
                    )
                }
            }
        }
        return recipeLiveData as LiveData<RecipesListWrapper>
    }

    data class RecipesListWrapper(
        val result: List<Recipe>? = null,
        val error: ErrorObject? = null,
    )
}