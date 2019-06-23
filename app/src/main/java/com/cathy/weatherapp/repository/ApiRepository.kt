package com.cathy.weatherapp.repository

import com.cathy.weatherapp.api.ApiService
import com.cathy.weatherapp.api.WeatherApi
import com.cathy.weatherapp.model.geocoding.GeocodingResponse
import com.cathy.weatherapp.model.search.City
import com.cathy.weatherapp.model.search.ResponseData
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ApiRepository {

    fun search(text: String): Observable<ResponseData> {
        val service = ApiService.getRetrofit().create(WeatherApi::class.java)
        return service.searchByName(text, ApiService().key)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    fun loadAddress(cities: List<City>): Observable<List<City>> {
        val service = ApiService.getRetrofit().create(WeatherApi::class.java)

        return Observable.fromIterable(cities)
            .flatMap (
                { city ->
                    getAddress(city, service)
                },
                {
                    city: City, address: GeocodingResponse ->
                    if (address.status == "OK"){
                        address.results?.let {
                            if (it.size > 1){
                                city.address = it[it.size-2].formattedAddress.toString()
                            } else {
                                city.address = it[0].formattedAddress.toString()
                            }
                        }
                    }
                    city
                })
            .toList()
            .toObservable()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun getAddress(data: City): Observable<City> {
        val service = ApiService.getRetrofit().create(WeatherApi::class.java)
        return Observable.just(data)
                .flatMap ({ city ->
                    getAddress(city, service)
                }, { city: City, address: GeocodingResponse ->
                    if (address.status == "OK"){
                        address.results?.let {
                            if (it.size > 1){
                                city.address = it[it.size-2].formattedAddress.toString()
                            } else {
                                city.address = it[0].formattedAddress.toString()
                            }
                        }
                    }
                    city
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    private fun getAddress(city: City, service: WeatherApi): Observable<GeocodingResponse> {
        val url = "https://maps.googleapis.com/maps/api/geocode/json?key=AIzaSyBeH088i5GbAKvSUmPFliDNPDbnrzl-o08&language=ru&latlng=" +
                "${city.coord?.lat},${city.coord?.lon}"
        return service.getAddress(url)
    }

    fun search(lat: Double, lon: Double): Observable<City> {
        val service = ApiService.getRetrofit().create(WeatherApi::class.java)
        return service.searchByLocation(lat, lon, ApiService().key)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}