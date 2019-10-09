package com.test.weather.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import com.test.weather.R
import com.test.weather.utils.SharedPref


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        val navGraph = navController.navInflater.inflate(R.navigation.nav_graph)

        when {
            SharedPref.getString("city").isEmpty() -> navGraph.startDestination = R.id.cityInputFragment
            else -> navGraph.startDestination = R.id.homeFragment
        }

        navController.graph = navGraph
    }



}
