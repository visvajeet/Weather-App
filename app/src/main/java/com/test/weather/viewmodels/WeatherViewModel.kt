package com.test.weather.viewmodels

import android.app.Application
import android.os.Handler
import android.os.Looper
import androidx.lifecycle.*
import com.test.weather.R
import com.test.weather.database.getDatabase
import com.test.weather.utils.CheckNetwork
import com.test.weather.utils.showToast
import com.test.weather.weatherRepository.WeatherRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.reflect.jvm.internal.impl.util.Check


class WeatherViewModel(application: Application) : AndroidViewModel(application){


    enum class Status{LOADING,ERROR,DONE}

    private val viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)


      private val _status = MutableLiveData<Status>()
      val status : LiveData<Status> = _status
      val app = application 


    private val database = getDatabase(application)
    private val weatherRepository = WeatherRepository(database,_status)


    val weather = weatherRepository.weatherAndForecast


    lateinit var mainHandler: Handler

    private val updateTask = object : Runnable {
        override fun run() {
            refreshData()
            mainHandler.postDelayed(this, 300000)
        }
    }


    init {
        if(!CheckNetwork.isNetworkConnected(application)){
            application.showToast(application.getString(R.string.no_internet))
        }
        coroutineScope.launch {
            weatherRepository.refreshWeatherData()
        }

        mainHandler = Handler(Looper.getMainLooper())
        mainHandler.post(updateTask)
    }


    fun refreshData(){
        if(!CheckNetwork.isNetworkConnected(app)){
            app.showToast(app.getString(R.string.no_internet))
        }
        coroutineScope.launch {
            weatherRepository.refreshWeatherData()
        }
    }


    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
        mainHandler.removeCallbacks(updateTask)
    }



    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(WeatherViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return WeatherViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }

}