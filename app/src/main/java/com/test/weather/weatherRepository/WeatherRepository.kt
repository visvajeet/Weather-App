package com.test.weather.weatherRepository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.test.weather.database.DatabaseWeather
import com.test.weather.database.WeatherDatabase
import com.test.weather.database.toDomainModel
import com.test.weather.domain.Weather
import com.test.weather.network.ForecastDatabaseModel
import com.test.weather.network.WeatherApi
import com.test.weather.utils.SharedPref
import com.test.weather.viewmodels.WeatherViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class WeatherRepository(
     private val database: WeatherDatabase,
     private val _status: MutableLiveData<WeatherViewModel.Status>
) {

    val weatherAndForecast: LiveData<Weather> = Transformations.map(database.weatherDao.getDatabaseWeather()) {
        it?.let {
            it.toDomainModel()
        }
    }


    suspend fun refreshWeatherData() {
        withContext(Dispatchers.IO) {


            try {
                _status.postValue(WeatherViewModel.Status.LOADING)
                WeatherApi.service.getWeather(city = SharedPref.getString("city")).let { weather ->
                    WeatherApi.service.getForecast(id = weather.id?:0).let { forecast ->


                         val forecastListDB = forecast.forecastList.map { ForecastDatabaseModel(it.dateString,
                             it.dateTimestamp,it.main?.temp,it.main?.tempMin,it.main?.tempMax,
                             it.weatherMoreInfo?.get(0)?.main,it.weatherMoreInfo?.get(0)?.description,it.weatherMoreInfo?.get(0)?.icon)  }


                         val currentWeatherAndForecast = DatabaseWeather(0,weather.name,weather.main?.temp,weather.main?.tempMin,weather.main?.tempMax,
                            weather.weatherMoreInfo?.get(0)?.main,  weather.weatherMoreInfo?.get(0)?.description, weather.weatherMoreInfo?.get(0)?.icon,forecastListDB)

                         database.weatherDao.insert(currentWeatherAndForecast)
                        _status.postValue(WeatherViewModel.Status.DONE)
                    }

                }
            } catch (exception: Exception) {
                _status.postValue(WeatherViewModel.Status.ERROR)
                Log.e("WeatherRepository", exception.message)
            }



        }
    }
}
