package com.mobiquity.network

import android.text.TextUtils
import com.google.gson.JsonObject
import com.mobiquity.network.model.WeatherApiResponse
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

interface APIService {

    @GET("weather")
    fun getWeather(@Query ("lat") lat : Double, @Query("lon") lon :Double, @Query("appid") appId:String): Call<WeatherApiResponse>

    companion object Factory {

        fun create(): APIService {
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
            val httpClient = OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .connectTimeout(30, TimeUnit.SECONDS).readTimeout(5 * 60, TimeUnit.SECONDS).writeTimeout(5 * 60, TimeUnit.SECONDS)

            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .baseUrl("http://api.openweathermap.org/data/2.5/").build()
            return retrofit.create(APIService::class.java)
        }
    }

}