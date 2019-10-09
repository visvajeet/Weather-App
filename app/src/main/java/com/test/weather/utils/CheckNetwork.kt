package com.test.weather.utils

import android.content.Context
import android.net.ConnectivityManager


object CheckNetwork {

     fun isNetworkConnected(context:Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager// 1
        val networkInfo = connectivityManager.activeNetworkInfo // 2
        return networkInfo != null && networkInfo.isConnected // 3
    }

}
