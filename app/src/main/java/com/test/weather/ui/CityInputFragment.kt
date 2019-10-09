package com.test.weather.ui


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.test.weather.R
import com.test.weather.databinding.CityInputFragmentBinding
import com.test.weather.utils.SharedPref
import com.test.weather.utils.hideKeyboard
import com.test.weather.utils.showToast


class CityInputFragment : Fragment() {

    lateinit var binding: CityInputFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = DataBindingUtil.inflate(inflater,R.layout.city_input_fragment,container,false)


        //Is app first time installed
        checkFirstTimeAppInstalled()

        //Done press
        binding.ivDone.setOnClickListener {
            cityInputDone()
        }

        //Back press
        binding.ivBack.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.etCityName.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                cityInputDone()
                true
            } else false
        }


        return binding.root

    }

    private fun cityInputDone() {

        val city = binding.etCityName.text.toString()
        if(city.trim().isEmpty()){
            context?.showToast(getString(R.string.city_not_blank))
        }else{
            this.activity?.hideKeyboard()
            SharedPref.setString("city",city)
            SharedPref.setBoolean("cityChanged",true)
            findNavController().navigate(CityInputFragmentDirections.actionCityInputFragmentToHomeFragment())

        }
    }


    private fun checkFirstTimeAppInstalled() {

        when {
            SharedPref.getString("city").isEmpty() -> binding.ivBack.visibility = View.GONE
            else -> binding.ivBack.visibility = View.VISIBLE
        }
    }


}
