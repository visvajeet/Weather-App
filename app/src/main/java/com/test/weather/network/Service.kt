package com.test.weather.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


private const val BASE_URL = "https://api.openweathermap.org/"
private const val API_KEY = "b393342df90714e4b128cdadbc049988"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()



interface WeatherApiService {

    @GET("data/2.5/weather")
    suspend fun getWeather( @Query("APPID") string: String = API_KEY, @Query("q") city:String, @Query("units") unit:String = "metric"): TodayWeather

    @GET("data/2.5/forecast")
    suspend fun getForecast( @Query("APPID") string: String = API_KEY, @Query("id") id:Long,  @Query("units") unit:String = "metric"): Forecast

}



object WeatherApi{

    val service : WeatherApiService by lazy {
        retrofit.create(WeatherApiService::class.java)
    }
}