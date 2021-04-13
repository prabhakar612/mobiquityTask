package com.mobiquity.network

import com.mobiquity.MyApplication
import com.mobiquity.network.model.WeatherApiResponse
import com.mobiquity.utils.NetworkException
import com.mobiquity.utils.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class NetworkHelper {
    companion object {
        private val apiService by lazy {  MyApplication.getInstance()?.getAPIService()}


        @JvmStatic
        suspend fun getWeather(lat: Double, lon: Double):Result<WeatherApiResponse> = withContext(Dispatchers.IO) {

            try{
            val response = apiService?.getWeather(lat,lon,"fae7190d7e6433ec3a45285ffcf55c86")?.execute()

            if (response!!.isSuccessful) {
                return@withContext com.mobiquity.utils.Result.Success(response.body()!!)
            } else {

                return@withContext com.mobiquity.utils.Result.Error(NetworkException(response.errorBody()!!.string(), response.code()))
            }
        } catch (ex: Exception) {
            return@withContext com.mobiquity.utils.Result.Error(ex)
        }
        }
    }
}