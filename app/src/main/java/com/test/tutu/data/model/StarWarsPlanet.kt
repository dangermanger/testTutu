package com.test.tutu.data.model


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName


@Entity(tableName = "planet")
data class StarWarsPlanet(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @SerializedName("climate")
    @ColumnInfo(name = "climate")
    val climate: String = "",
    @SerializedName("created")
    @ColumnInfo(name ="created")
    val created: String = "",
    @SerializedName("diameter")
    @ColumnInfo(name ="diameter")
    val diameter: String = "",
    @SerializedName("edited")
    @ColumnInfo(name ="edited")
    val edited: String = "",
    @SerializedName("films")
    @ColumnInfo(name ="films")
    val films: List<String> = emptyList(),
    @SerializedName("gravity")
    @ColumnInfo(name ="gravity")
    val gravity: String = "",
    @SerializedName("name")
    @ColumnInfo(name ="name")
    val name: String = "",
    @SerializedName("orbital_period")
    @ColumnInfo(name ="orbital_period")
    val orbitalPeriod: String = "",
    @SerializedName("population")
    @ColumnInfo(name ="population")
    val population: String = "",
    @SerializedName("residents")
    @ColumnInfo(name ="residents")
    val residents: List<String> = emptyList(),
    @SerializedName("rotation_period")
    @ColumnInfo(name ="rotation_period")
    val rotationPeriod: String = "",
    @SerializedName("surface_water")
    @ColumnInfo(name ="surface_water")
    val surfaceWater: String = "",
    @SerializedName("terrain")
    @ColumnInfo(name ="terrain")
    val terrain: String = "",
    @SerializedName("url")
    @ColumnInfo(name ="url")
    val url: String = "5"
)
