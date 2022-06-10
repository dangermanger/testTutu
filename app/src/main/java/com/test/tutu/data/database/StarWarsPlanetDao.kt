package com.test.tutu.data.database

import androidx.room.*
import com.test.tutu.data.model.StarWarsPlanet

@Dao
interface StarWarsPlanetDao {
    @Query("SELECT * FROM planet")
    suspend fun getAll(): List<StarWarsPlanet>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(starWarsPlanet: List<StarWarsPlanet>)
}