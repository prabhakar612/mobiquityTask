package com.mobiquity.view.fragment

import android.content.Context
import android.content.SharedPreferences
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.mobiquity.R
import com.mobiquity.databinding.MapsFragmentBinding
import com.mobiquity.view.MapsActivity
import com.mobiquity.view.adapter.CityListRecyclerAdapter
import com.mobiquity.view.model.CityModel
import com.mobiquity.viewmodel.MainViewmodel
import java.io.IOException
import java.lang.reflect.Type
import java.util.*


class MapsFragment:Fragment(), OnMapReadyCallback {
    private lateinit var mMap: GoogleMap
    lateinit var  binding : MapsFragmentBinding
    lateinit var viewModel :MainViewmodel
    var cityList= arrayListOf<CityModel>()

    val preferences by lazy { getSharedPreferences("main", Context.MODE_PRIVATE) }
    val prefsEditor by lazy { preferences.edit() }

    private fun getSharedPreferences(prrefsName: String, modePrivate: Int): SharedPreferences{
        return context!!.getSharedPreferences(prrefsName, modePrivate)

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewmodel::class.java)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.maps_fragment, null, false)
        (activity as MapsActivity).setTitle("Weather App")

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val layoutManager = activity?.let { LinearLayoutManager(it) }
        binding?.locationList?.layoutManager = layoutManager
        binding?.locationList?.hasFixedSize()
        binding?.locationList?.adapter = CityListRecyclerAdapter(context, viewModel, cityList)
        val json = preferences.getString("citylist", "")
        json?.let {
            if (it.isNullOrEmpty().not()) {
                val gson = Gson()
                val type: Type = object : TypeToken<ArrayList<CityModel>>() {}.type
                cityList.addAll( gson.fromJson(json, type))
                viewModel.cityList.value = cityList

            }
        }
        observeLiveData()

    }

    fun observeLiveData(){
        viewModel.cityList.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            it?.let {
               // cityList.addAll(it)
                binding?.locationList?.adapter?.notifyDataSetChanged()
                val gson = Gson()
                val json = gson.toJson(it)
                prefsEditor.putString("citylist", json)
                prefsEditor.commit()

            }
        })

        viewModel.navigateToCityScreen.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            it?.let { cityModel ->
                activity?.let {
                    val fragmentTransaction = activity!!.supportFragmentManager.beginTransaction()
                    fragmentTransaction.replace(
                        R.id.map_root, CityFragment.getInstance(
                            cityModel.lat,
                            cityModel.long
                        )
                    )
                    fragmentTransaction.commit()
                }
            }
        })
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        val mapFragment =
            childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera
        mMap.uiSettings.isZoomControlsEnabled = true

        val madrid = LatLng(28.7041, 77.1025)
        // val sydney = LatLng(-34.0, 151.0)
        mMap.addMarker(MarkerOptions().position(madrid).title("Marker in India"))
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(madrid, 3F));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(madrid))
        mMap.setOnMapClickListener {
            // allPoints.add(it)
            mMap.clear()
            mMap.addMarker(MarkerOptions().position(it))
            getCityName(it.latitude, it.longitude)
        }

    }

    fun getCityName(lat: Double, long: Double){
        val gcd = Geocoder(activity, Locale.getDefault())
        var addresses: List<Address>? = null
        try {
            addresses = gcd.getFromLocation(lat, long, 1)
        } catch (e: IOException) {
            e.printStackTrace()
        }
        if (addresses != null && addresses.isNotEmpty()) {

            try {
                val city = CityModel(
                    addresses[0].latitude,
                    addresses[0].longitude,
                    "${addresses[0].featureName}, ${addresses[0].adminArea}"
                )
                cityList.add(city)
                viewModel.cityList.value = cityList
            }catch (ex: Exception){
                Toast.makeText(context, ex.message, Toast.LENGTH_LONG).show()
            }
        }
    }



    companion object {
        fun getInstance() : MapsFragment {
            return MapsFragment()
        }
    }
}