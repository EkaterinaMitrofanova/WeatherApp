package com.cathy.weatherapp.database

import androidx.room.*
import com.cathy.weatherapp.model.search.City
import io.reactivex.Flowable
import io.reactivex.Maybe

@Dao
interface WeatherDao {

    @Insert
    fun insertCity(city: City)

    @Query("SELECT * FROM city ORDER BY name")
    fun getCities(): Flowable<List<City>>

    @Delete
    fun deleteCity(city: City)

    @Query("SELECT * FROM city WHERE id =:id")
    fun getCityById(id: Long): Maybe<City>

    @Update
    fun update(city: City)
}