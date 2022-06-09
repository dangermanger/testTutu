package com.test.tutu.data

import com.test.tutu.data.model.ListStarWarsPlanets
import com.test.tutu.data.model.StarWarsPlanet
import retrofit2.Response

import javax.inject.Inject


class ApiHelperImpl @Inject constructor(
    private val apiService: ApiService
) : ApiHelper {
    override suspend fun getPlanets(): Response<ListStarWarsPlanets> = apiService.getPlanets()
    override suspend fun getDetail(id: Int): Response<StarWarsPlanet> = apiService.getDetail(id)
}