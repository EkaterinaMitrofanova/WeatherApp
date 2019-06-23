package com.cathy.weatherapp.listeners

import com.cathy.weatherapp.model.search.City

interface EventListener {

    fun onClick(city: City)
}