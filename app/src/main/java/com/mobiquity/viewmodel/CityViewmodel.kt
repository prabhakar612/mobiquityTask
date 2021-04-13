package com.mobiquity.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobiquity.network.NetworkHelper
import com.mobiquity.network.model.WeatherApiResponse
import kotlinx.coroutines.launch

class CityViewmodel : ViewModel() {

    val weatherLiveData = MutableLiveData<WeatherApiResponse>()

   fun  getweatherApi(lat:Double,lon:Double){
       viewModelScope.launch {
           val weatherResponse = NetworkHelper.getWeather(lat,lon)
           when (weatherResponse) {
               is com.mobiquity.utils.Result.Success -> {
                   weatherLiveData.value =weatherResponse.data

               }
               is com.mobiquity.utils.Result.Error -> {

               }
           }

       }
   }
}