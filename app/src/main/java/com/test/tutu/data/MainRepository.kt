package com.test.tutu.data

import com.test.tutu.data.model.ListStarWarsPlanets
import com.test.tutu.data.model.StarWarsPlanet
import com.test.tutu.utils.BaseApiResponse
import com.test.tutu.utils.NetworkResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val apiHelper:ApiHelper
): BaseApiResponse(){

   suspend fun getPlanets(): NetworkResult<ListStarWarsPlanets>{
       return withContext(Dispatchers.IO){ safeApiCall { apiHelper.getPlanets() }}
   }

    suspend fun getDetail(id: Int): NetworkResult<StarWarsPlanet>{
        return withContext(Dispatchers.IO){ safeApiCall { apiHelper.getDetail(id) }}
    }

}