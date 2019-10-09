/*
 * Copyright 2018, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.test.weather.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.test.weather.domain.Weather
import com.test.weather.network.ForecastDatabaseModel
import kotlinx.coroutines.channels.ConflatedBroadcastChannel


@Entity
data class DatabaseWeather(
                   @PrimaryKey()
                   val id:Long?,
                   val name:String?,
                   val temp:Double?,
                   val tempMin:Double?,
                   val tempMax:Double?,
                   val main:String?,
                   val description: String?,
                   val icon: String?,
                   val list: List<ForecastDatabaseModel>

)


class Converters {
    @TypeConverter
    fun fromString(value: String): List<ForecastDatabaseModel> {
        val listType = object : TypeToken<List<ForecastDatabaseModel>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromList(list: List<ForecastDatabaseModel>): String {
        return Gson().toJson(list)
    }
}

fun DatabaseWeather.toDomainModel(): Weather {

    return  Weather(id, name, temp, tempMin, tempMax, main, description, icon, list)

}

