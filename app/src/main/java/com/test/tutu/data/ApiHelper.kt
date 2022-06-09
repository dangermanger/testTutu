package com.test.tutu.data

import com.test.tutu.data.model.ListStarWarsPlanets
import com.test.tutu.data.model.StarWarsPlanet
import retrofit2.Response

interface ApiHelper {
    suspend fun getPlanets(): Response<ListStarWarsPlanets>
    suspend fun getDetail(id: Int): Response<StarWarsPlanet>
}