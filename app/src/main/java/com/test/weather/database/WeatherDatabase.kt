package com.test.weather.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*


@Dao
interface WeatherDao {
    @Query("select * from databaseweather")
    fun getDatabaseWeather(): LiveData<DatabaseWeather>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(databaseWeather: DatabaseWeather)
}


@Database(entities = [DatabaseWeather::class], version = 1)
@TypeConverters(Converters::class)
abstract class WeatherDatabase : RoomDatabase() {
    abstract val weatherDao: WeatherDao
}



private lateinit var INSTANCE: WeatherDatabase

fun getDatabase(context: Context): WeatherDatabase {
    synchronized(WeatherDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(context.applicationContext,
                WeatherDatabase::class.java,
                "weather").build()
        }
    }
    return INSTANCE
}



