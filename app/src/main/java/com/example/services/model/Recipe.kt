package com.example.services.model

import com.google.gson.annotations.SerializedName

data class Recipe(
    @SerializedName("calories")
    val calories: String,
    @SerializedName("carbos")
    val carbos: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("thumb")
    val thumb: String? = null,
    @SerializedName("image")
    val image: String? = null,
    @SerializedName("time")
    val time: String,
    @SerializedName("headline")
    val headline: String,
)