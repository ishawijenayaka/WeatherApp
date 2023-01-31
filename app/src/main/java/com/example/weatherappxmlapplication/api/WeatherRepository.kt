package com.example.weatherappxmlapplication.api

import javax.inject.Inject

class WeatherRepository
@Inject
constructor(private val apiService: ApiService){

//    suspend fun getCurrentWeather(lat : Double, lon : Double, appId : String) = apiService.getCurrentWeather(lat, lon, appId)

    suspend fun getCityWeather(city:String, appId:String) = apiService.getTownWeather(city, appId)
}