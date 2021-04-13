package com.mobiquity.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.mobiquity.R
import com.mobiquity.databinding.CityFragmentBinding
import com.mobiquity.databinding.MapsFragmentBinding
import com.mobiquity.view.MapsActivity
import com.mobiquity.view.adapter.CityListRecyclerAdapter
import com.mobiquity.viewmodel.CityViewmodel
import com.mobiquity.viewmodel.MainViewmodel

class CityFragment:Fragment() {
    lateinit var  binding : CityFragmentBinding
    lateinit var viewModel :CityViewmodel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(CityViewmodel::class.java)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.city_fragment, null, false)
        (activity as MapsActivity).setTitle("City Weather")

        arguments?.let {
            val lat = it.getDouble("lat",0.0)
            val lon = it.getDouble("lon",0.0)

            viewModel.getweatherApi(lat,lon)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeLiveData()
    }
    fun observeLiveData(){
        viewModel.weatherLiveData.observe(viewLifecycleOwner, Observer {
            binding.viewmodel = viewModel
            binding.weatherApiResponse =it

        })
    }

    companion object{
        fun getInstance(lat:Double,lon :Double):CityFragment{

            val bundle=Bundle()
            bundle.putDouble("lat",lat)
            bundle.putDouble("lon",lon)
            val fragment =CityFragment()
            fragment.arguments = bundle
            return  fragment


        }
    }
}