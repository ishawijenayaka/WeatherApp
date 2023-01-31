package com.example.weatherappxmlapplication.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherappxmlapplication.api.WeatherRepository
import com.example.weatherappxmlapplication.model.FullWeatherResponse
import com.example.weatherappxmlapplication.model.Main
import com.example.weatherappxmlapplication.model.Weather
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel
@Inject
constructor(private val weatherRepository: WeatherRepository) : ViewModel(){

    private val _responseCity = MutableLiveData<FullWeatherResponse>()
    val responseWeatherCity : MutableLiveData<FullWeatherResponse> get() = _responseCity



    fun getCityWeather(city : String, appId : String) = viewModelScope.launch {
        weatherRepository.getCityWeather(city, appId).let { responseC ->
            if (responseC.isSuccessful){
                _responseCity.postValue(responseC.body())
            }else{
                Log.d("tag","getCityWeather show error")

            }
        }
    }


}