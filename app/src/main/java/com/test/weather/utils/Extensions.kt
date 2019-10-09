package com.test.weather.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.test.weather.ForecastAdapter
import com.test.weather.network.ForecastDatabaseModel
import com.test.weather.network.ForecastDomain
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashSet

fun Context.showToast(str:String){
    Toast.makeText(this,str, Toast.LENGTH_LONG).show()
}


fun Activity.hideKeyboard(){
    val imm = this.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    var view = this.currentFocus
    if (view == null) {
        view = View(this)
    }
    imm.hideSoftInputFromWindow(view.windowToken, 0)
}



@SuppressLint("SimpleDateFormat")
@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<ForecastDatabaseModel>?) {

data?.let {

    val adapter = recyclerView.adapter as ForecastAdapter

    val filter = ArrayList<ForecastDomain>()
    filter.clear()

    val sdf = SimpleDateFormat("EEE")
    for(item in data){

        if(item.dateString.substring(11,13) == "12"){
            val dayString = sdf.format(Date(item.dateTimestamp!!*1000))
            filter.add(ForecastDomain(dayString,item.temp,item.tempMin,item.tempMax,item.icon))
        }
    }

    adapter.submitList(filter)

}

}

@BindingAdapter("bindImage")
fun bindImage(iv: ImageView, icon:String?) {

    icon?.let {
        Picasso.get().load("http://openweathermap.org/img/w/$icon.png").into(iv)
    }

}


