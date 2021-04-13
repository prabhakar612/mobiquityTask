package com.mobiquity

import android.app.Application
import com.mobiquity.network.APIService

class MyApplication : Application(){
    private var apiService: APIService? = null

    override fun onCreate() {
        super.onCreate()
        mInstance = this
    }

    @Synchronized
    fun getAPIService(): APIService? {
        if (apiService == null) {
            apiService = APIService.create()
        }
        return apiService
    }

    companion object{
         var mInstance: MyApplication? = null

        fun getInstance(): MyApplication? {
            return mInstance
        }
    }


}