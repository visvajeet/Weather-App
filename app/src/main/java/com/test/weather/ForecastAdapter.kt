package com.test.weather

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.test.weather.databinding.ForecastModelBinding
import com.test.weather.network.ForecastDomain

class ForecastAdapter : ListAdapter<ForecastDomain, ForecastAdapter.MarsPropertyViewHolder>(DiffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForecastAdapter.MarsPropertyViewHolder {
        return MarsPropertyViewHolder(ForecastModelBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: ForecastAdapter.MarsPropertyViewHolder, position: Int) {
        val forecast = getItem(position)
        holder.bind(forecast)
    }

    class MarsPropertyViewHolder(private var binding: ForecastModelBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(forecast: ForecastDomain) {
            binding.data = forecast
            binding.executePendingBindings()
        }
    }



    companion object DiffCallback : DiffUtil.ItemCallback<ForecastDomain>() {
        override fun areItemsTheSame(oldItem: ForecastDomain, newItem: ForecastDomain): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: ForecastDomain, newItem: ForecastDomain): Boolean {
            return oldItem.day == newItem.day
        }
    }



}


