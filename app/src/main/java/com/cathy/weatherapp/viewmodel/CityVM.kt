package com.cathy.weatherapp.viewmodel

import android.annotation.SuppressLint
import android.app.Application
import android.util.Log
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.cathy.weatherapp.model.search.City
import com.cathy.weatherapp.model.search.ResponseData
import com.cathy.weatherapp.repository.ApiRepository
import com.cathy.weatherapp.repository.DBRepository

class CityVM(private val app: Application) : AndroidViewModel(app){

    private val apiRepo = ApiRepository()
    private val dbRepo = DBRepository(app)
    private val data = MutableLiveData<List<City>>()
    private val progress = MutableLiveData<Int>()
    private val message = MutableLiveData<String>()
    private val addedCities = MutableLiveData<MutableList<City>>()


    fun getProgress(): LiveData<Int> = progress
    fun getData(): LiveData<List<City>> = data
    fun getMessage(): LiveData<String> = message
    fun getAddedCities(): LiveData<MutableList<City>> = addedCities

    @SuppressLint("CheckResult")
    fun search(text: String){
        if (text.isEmpty()){
            return
        }
        apiRepo.search(text)
            .subscribe(
                {
                    if (it.count == 0){
                        message.value = "Ничего не найдено"
                        this.data.value = it.list
                        progress.value = View.INVISIBLE
                    } else {
                        loadAddress(it)
                    }
                },
                {
                    Log.e("CityVM", "search(): $it")
                    progress.value = View.INVISIBLE
                    message.value = it.message
                },
                {},
                {
                    progress.value = View.VISIBLE
                })
    }

    @SuppressLint("CheckResult")
    fun search(pair: Pair<Double?, Double?>){
        if (pair.first == null || pair.second == null){
            message.value = "Не удалось определить местоположение"
            return
        }
        progress.value = View.VISIBLE
        apiRepo.search(pair.first!!, pair.second!!)
            .subscribe({city ->
                loadAddress(city)
            }, {
                Log.e("CityVM", "searchByLocation(): $it")
                progress.value = View.INVISIBLE
                message.value = it.message
            })
    }

    @SuppressLint("CheckResult")
    private fun loadAddress(city: City){
        apiRepo.getAddress(city)
            .subscribe({
                addCity(city)
            }, {
                Log.e("CityVM", "loadAddress(): $it")
                progress.value = View.INVISIBLE
                message.value = it.message
            })
    }

    private fun loadAddress(data: ResponseData){
        data.list?.let { list ->
            apiRepo.loadAddress(list)
                .subscribe({
                    this.data.value = it
                    progress.value = View.INVISIBLE
                }, {
                    Log.e("CityVM", "loadAddress(): $it")
                    progress.value = View.INVISIBLE
                    message.value = it.message
                })
        }
    }

    @SuppressLint("CheckResult")
    fun addCity(city: City){
        progress.value = View.VISIBLE

        city.id?.let {
            dbRepo.getCityById(it)
                .subscribe(
                    {
                        progress.value = View.INVISIBLE
                        message.value = "Город ${city.name} уже добавлен"
                    },
                    {
                        Log.e("CityVM", ".getCityById(): $it")
                        progress.value = View.INVISIBLE
                    },
                    {
                        insert(city)
                    }
                )
        }
    }

    @SuppressLint("CheckResult")
    private fun insert(city: City){
        dbRepo.insertCity(city)
            .subscribe({}, {
                Log.e("CityVM", "insert(): $it")
                progress.value = View.INVISIBLE
                message.value = "Не удалось добавить город"
            }, {
                progress.value = View.INVISIBLE
                if (addedCities.value == null){
                    addedCities.value = mutableListOf(city)
                } else{
                    addedCities.value!!.add(city)
                    addedCities.value = addedCities.value
                }
            })
    }

    @SuppressLint("CheckResult")
     fun deleteCity(city: City){
        dbRepo.deleteCity(city)
            .subscribe({}, {
                Log.e("CityVM", "delete(): $it")
                message.value = "Не удалось удалить город"
            }, {
                addedCities.value?.remove(city)
                addedCities.value = addedCities.value
            })
    }
}