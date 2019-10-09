package com.test.weather

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.test.weather.database.getDatabase
import com.test.weather.utils.SharedPref
import com.test.weather.viewmodels.WeatherViewModel
import com.test.weather.weatherRepository.WeatherRepository
import retrofit2.HttpException

class RefreshWeatherData(context: Context, params: WorkerParameters): CoroutineWorker(context,params){


    companion object {
        const val WORK_NAME = "RefreshWeather"
    }

    override suspend fun doWork(): Result {


         val status = MutableLiveData<WeatherViewModel.Status>()
         val database = getDatabase(applicationContext)
         val repository = WeatherRepository(database,status)



        return try {
            repository.refreshWeatherData()
            Result.success()
        } catch (e: Exception) {
            Log.d("RefreshWeather",e.message)
            Result.retry()
        }
    }


}