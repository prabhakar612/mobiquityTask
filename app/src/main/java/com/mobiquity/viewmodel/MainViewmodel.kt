package com.mobiquity.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mobiquity.view.model.CityModel

class MainViewmodel: ViewModel() {

    val cityList = MutableLiveData<ArrayList<CityModel>>()
    val navigateToCityScreen = MutableLiveData<CityModel>()

    fun deleteCityName( position:Int){
        val mCityList = cityList.value
        mCityList?.removeAt(position)
        cityList.value = mCityList

    }

    fun cickOnCityItem(city:CityModel){
        navigateToCityScreen.value =city
    }
}