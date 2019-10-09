package com.test.weather.utils

import android.content.Context
import android.content.SharedPreferences
import android.widget.Toast
import androidx.core.content.edit
import java.util.*

object SharedPref{


    private lateinit var pref:SharedPreferences

    fun data(context: Context){
        val appName="weather"
        pref = context.applicationContext.getSharedPreferences(appName,
            Context.MODE_PRIVATE
        )!!

    }


    fun setBoolean(key:String,value:Boolean){
        pref.edit {
            putBoolean(key,value)
        }
    }

    fun setString(key:String,value:String){

        pref.edit {
            putString(key,value)
        }

    }

    fun getBoolean(key:String): Boolean {

        return pref.getBoolean(key,false)

    }

    fun getString(key:String): String {

        return pref.getString(key,"")!!

    }



}