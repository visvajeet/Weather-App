package com.test.weather.ui


import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.test.weather.ForecastAdapter
import com.test.weather.R
import com.test.weather.databinding.HomeFragmentBinding
import com.test.weather.utils.SharedPref
import com.test.weather.viewmodels.WeatherViewModel
import kotlinx.android.synthetic.main.home_fragment.*


class HomeFragment : Fragment() {

    lateinit var binding: HomeFragmentBinding

    private val viewModel: WeatherViewModel by lazy {
        val activity = requireNotNull(this.activity)
        ViewModelProviders.of(this, WeatherViewModel.Factory(activity.application)).get(WeatherViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = DataBindingUtil.inflate(inflater,R.layout.home_fragment,container,false)


        binding.viewModel = viewModel
        binding.lifecycleOwner = this


        binding.ivSetCity.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToCityInputFragment())
        }


        if(SharedPref.getBoolean("cityChanged")){
            viewModel.refreshData()
            SharedPref.setBoolean("cityChanged",false)
        }

        val adapter= ForecastAdapter()
        binding.recyclerView.adapter = adapter

        binding.srl.setColorSchemeColors(ContextCompat.getColor(context!!,R.color.colorPrimary))
        binding.srl.setOnRefreshListener { viewModel.refreshData() }


        viewModel.status.observe(this, Observer {

            it?.let {
                when(it){
                    WeatherViewModel.Status.LOADING -> srl.isRefreshing = true
                    WeatherViewModel.Status.ERROR -> srl.isRefreshing = false
                    WeatherViewModel.Status.DONE -> srl.isRefreshing = false
                }
            }
        })


        return binding.root

    }


}
