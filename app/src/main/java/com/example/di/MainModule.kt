package com.hellofresh.task2.di

import com.example.BuildConfig
import com.google.gson.GsonBuilder
import com.google.gson.internal.bind.DateTypeAdapter
import com.example.app.Constants
import com.example.services.ErrorInterceptor
import com.example.services.HelloFreshApi
import com.example.services.repository.RecipesRepository
import com.hellofresh.task2.services.repository.*
import com.example.ui.home.HomeViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import java.util.concurrent.TimeUnit


val viewModels = module {
    viewModel { HomeViewModel(get()) }
    single { RecipesImpl(get()) as RecipesRepository }
}

val remoteDataModule = module {

    single {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        interceptor
    }

    single { GsonBuilder().registerTypeAdapter(Date::class.java, DateTypeAdapter()).create() }

    single { GsonConverterFactory.create(get()) }

    single {


        /*val apiVersionHeader = Interceptor { chain ->
            chain.proceed(
                chain.request().newBuilder()
                    .addHeader("Accept-Version", "1")
                    .build()
            )
        }*/

        OkHttpClient.Builder()
            .connectTimeout(Constants.NETWORK_TIME_OUT, TimeUnit.SECONDS)
            .readTimeout(Constants.NETWORK_TIME_OUT, TimeUnit.SECONDS)
            .addInterceptor(get() as HttpLoggingInterceptor)
            //.addInterceptor(NetworkInterceptor(androidContext()))
            .addInterceptor(ErrorInterceptor())
            .build()
    }

    single {
        Retrofit.Builder()
            .baseUrl(BuildConfig.API_BASE_URL)
            .addConverterFactory(get() as GsonConverterFactory)
            .client(get() as OkHttpClient)
            .build()
    }

    single {
        (get() as Retrofit).create(
            HelloFreshApi::class.java
        )
    }
}

