package com.cathy.weatherapp.api

import com.cathy.weatherapp.model.geocoding.GeocodingResponse
import com.cathy.weatherapp.model.search.City
import com.cathy.weatherapp.model.search.ResponseData
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface WeatherApi {

    @GET("find?lang=ru&units=metric")
    fun searchByName(@Query("q") city: String, @Query("appid") key: String): Observable<ResponseData>

    @GET
    fun getAddress(@Url url: String): Observable<GeocodingResponse>

    @GET("weather?lang=ru&units=metric")
    fun getById(@Query("id") id: Long, @Query("appid") key: String): Observable<City>

    @GET("weather?lang=ru&units=metric")
    fun searchByLocation(@Query("lat") lat: Double,
                         @Query("lon") lon: Double,
                         @Query("appid") key: String): Observable<City>

}