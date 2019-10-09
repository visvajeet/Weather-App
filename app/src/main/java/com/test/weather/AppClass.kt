package com.test.weather

import android.app.Application
import android.os.Build
import androidx.work.*
import com.test.weather.utils.SharedPref
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class AppClass : Application() {

    val scope = CoroutineScope(Dispatchers.Default)

    override fun onCreate() {
        super.onCreate()
        SharedPref.data(this)

       /* delayedInit()*/

    }

   /* private fun delayedInit() = scope.launch {
        setupRecurringWork()
    }*/

    /*val constraints = Constraints.Builder()
        .setRequiredNetworkType(NetworkType.UNMETERED)
        .setRequiresBatteryNotLow(true)
        .setRequiresCharging(false)
        .apply {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                setRequiresDeviceIdle(true)
            }
        }.build()
*/

    /*private fun setupRecurringWork() {
        val repeatingRequest = PeriodicWorkRequestBuilder<RefreshWeatherData>(15, TimeUnit.MINUTES)
            .setConstraints(constraints)
            .build()

        WorkManager.getInstance().enqueueUniquePeriodicWork(
            RefreshWeatherData.WORK_NAME,
            ExistingPeriodicWorkPolicy.KEEP,
            repeatingRequest)
    }*/



}