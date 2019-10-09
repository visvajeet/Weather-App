package com.test.weather.network

import com.squareup.moshi.Json


data class TodayWeatherMainInfo( val temp:Double?,
                 val pressure:Double?,
                 val humidity:Int?,
                 @Json(name = "temp_min")
                 val tempMin: Double?,
                 @Json(name = "temp_max")
                 val tempMax: Double?
)

data class TodayWeatherMoreInfo(val id:Long?,
                val main:String?,
                val description: String?,
                val icon: String?
)


data class TodayWeather(val id:Long?,
                   val timezone:Long?,
                   val name:String?,
                   @Json(name = "main")
                   val main:TodayWeatherMainInfo?,
                   @Json(name = "weather")
                   val weatherMoreInfo: List<TodayWeatherMoreInfo>?
)



data class City(val name: String?, val id: Long?)

data class ForecastList( @Json(name = "main")
                         val main:TodayWeatherMainInfo?,
                         @Json(name = "weather")
                         val weatherMoreInfo: List<TodayWeatherMoreInfo>?,
                         @Json(name = "dt_txt")
                         val dateString:String,
                         @Json(name = "dt")
                         val dateTimestamp:Long?)


data class Forecast(
                   @Json(name = "cnt")
                   val count:Int,
                   val city: City,
                   @Json(name = "list")
                   val forecastList: List<ForecastList>
)

data class ForecastDatabaseModel(
    val dateString:String,
    val dateTimestamp:Long?,
    val temp: Double?,
    val tempMin: Double?,
    val tempMax: Double?,
    val main:String?,
    val description: String?,
    val icon: String?
)

data class ForecastDomain(
    val day:String,
    val temp: Double?,
    val tempMin: Double?,
    val tempMax: Double?,
    val icon: String?
)

