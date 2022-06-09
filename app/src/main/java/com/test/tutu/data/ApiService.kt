package com.test.tutu.data

import com.test.tutu.data.model.ListStarWarsPlanets
import com.test.tutu.data.model.StarWarsPlanet
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("api/planets/")
    suspend fun getPlanets(): Response<ListStarWarsPlanets>

    @GET("api/planets/{id}")
    suspend fun getDetail(
        @Path("id") id: Int
    ): Response<StarWarsPlanet>
}