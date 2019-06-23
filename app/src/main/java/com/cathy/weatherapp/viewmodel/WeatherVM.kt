package com.cathy.weatherapp.viewmodel

import android.annotation.SuppressLint
import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.cathy.weatherapp.model.search.City
import com.cathy.weatherapp.repository.DBRepository

class WeatherVM(val app: Application) : AndroidViewModel(app) {

    private val weather = MutableLiveData<List<City>>()
    private val dbRepo = DBRepository(app)
    private val progress = MutableLiveData<Boolean>()
    private val message = MutableLiveData<String>()

    fun getWeather(): LiveData<List<City>> = weather
    fun getMessage(): LiveData<String> = message
    fun getProgress(): LiveData<Boolean> = progress

    init {
        loadData()
    }

    @SuppressLint("CheckResult")
    private fun loadData(){
        progress.value = true
        dbRepo.getAllCities()
            .subscribe({
                run {
                    progress.value = false
                    weather.value = it
                }
            }, {
                Log.e("WeatherVM", "$it")
                progress.value = false
                message.value = "Не удалось загрузить данные"
            })
    }

    @SuppressLint("CheckResult")
    fun deleteCity(city: City){
//        weather.value?.let {
//            val city = it[position]
            dbRepo.deleteCity(city)
                .subscribe({}, {
                    Log.e("WeatherVM", "delete(): $it")
                    message.value = "Не удалось удалить город"
                }, {
                })
//        }
    }

    fun update(){
        weather.value?.let {
            dbRepo.update(it)
                .subscribe({}, {
                    Log.e("WeatherVM", "update(): $it")
                    message.value = "Не удалось обновить данные"
                    progress.value = false
                }, {
                    progress.value = false
                })
        }
    }

}