package com.test.tutu.data.database

import android.content.Context
import androidx.room.*
import androidx.room.Database
import com.test.tutu.data.model.StarWarsPlanet
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Database(entities = [StarWarsPlanet::class], version = 1)
@TypeConverters(Converter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun starWarsPlanetDao(): StarWarsPlanetDao
}

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            "StarWars"
        ).build()
    }

    @Singleton
    @Provides
    fun provideStarWarsPlanetDao(appDatabase: AppDatabase): StarWarsPlanetDao {
        return appDatabase.starWarsPlanetDao()
    }
}

class Converter {
    @TypeConverter
    fun convertToString(onTop: List<String>): String {
        println("Join To String" + onTop.joinToString { it })
        return onTop.toString()
    }

    @TypeConverter
    fun convertorToList(value: String): List<String> {
        println("Remove" + value.removePrefix("[").removeSuffix("]").split(","))
        return value.removePrefix("[").removeSuffix("]").split(",")
    }
}
