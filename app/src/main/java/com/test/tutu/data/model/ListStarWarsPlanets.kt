package com.test.tutu.data.model


import com.google.gson.annotations.SerializedName

data class ListStarWarsPlanets(
    @SerializedName("count")
    val count: Int,
    @SerializedName("next")
    val next: String,
    @SerializedName("previous")
    val previous: Any,
    @SerializedName("results")
    val results: List<StarWarsPlanet>
)