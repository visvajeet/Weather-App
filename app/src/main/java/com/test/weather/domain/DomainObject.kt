package com.test.weather.domain

import com.test.weather.network.ForecastDatabaseModel


data class Weather(val id:Long?,
                    val name:String?,
                    val temp:Double?,
                    val tempMin:Double?,
                    val tempMax:Double?,
                    val main:String?,
                    val description: String?,
                    val icon: String?,
                    val list: List<ForecastDatabaseModel>?

)