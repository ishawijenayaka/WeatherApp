package com.example.weatherappxmlapplication.api

import com.example.weatherappxmlapplication.model.FullWeatherResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

//    @GET("weather")
//    suspend fun getCurrentWeather(
//        @Query("lat") lat : Double,
//        @Query("lon") lon : Double,
//        @Query("appid") appId : String
//    ) : Response<FullWeatherResponse>

    @GET("weather")
    suspend fun getTownWeather(
        @Query("q") city : String,
        @Query("appid") appId : String
    ) : Response<FullWeatherResponse>


}