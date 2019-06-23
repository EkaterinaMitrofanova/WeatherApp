package com.cathy.weatherapp.repository

import android.content.Context
import com.cathy.weatherapp.api.ApiService
import com.cathy.weatherapp.api.WeatherApi
import com.cathy.weatherapp.database.AppDatabase
import com.cathy.weatherapp.database.WeatherDao
import com.cathy.weatherapp.model.search.City
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class DBRepository(val context: Context) {

    private var weatherDao: WeatherDao

    init {
        val db = AppDatabase.getInstance(context)
        weatherDao = db.weatherDao()
    }

    fun getAllCities(): Flowable<List<City>> {
        return weatherDao.getCities()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun insertCity(city: City): Observable<Unit> {
        return Observable.fromCallable {
            weatherDao.insertCity(city)
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun getCityById(id: Long): Maybe<City> {
        return weatherDao.getCityById(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun deleteCity(city: City): Observable<Unit> {
        return Observable.fromCallable {
            weatherDao.deleteCity(city)
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun update(list: List<City>): Observable<Unit> {
        val service = ApiService.getRetrofit().create(WeatherApi::class.java)
        return Observable.fromIterable(list)
            .flatMap ({city ->
                city.id?.let {
                        id -> service.getById(id, ApiService().key)
                }
            }, { city: City, newCity: City ->
                if (!city.equals(newCity)){
                    newCity.address = city.address
                    weatherDao.update(newCity)
                }
            })
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

}