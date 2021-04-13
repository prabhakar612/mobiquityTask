package com.mobiquity.view


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil

import com.mobiquity.R
import com.mobiquity.databinding.ActivityMapsBinding
import com.mobiquity.databinding.MapsFragmentBinding
import com.mobiquity.view.fragment.MapsFragment


class MapsActivity : AppCompatActivity() {


    lateinit var  binding : ActivityMapsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
         binding = DataBindingUtil.setContentView<ActivityMapsBinding>(this, R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.

        addMapViewFragment()
    }
    fun addMapViewFragment(){
            val fragmentTransaction = supportFragmentManager.beginTransaction()
            fragmentTransaction.add(R.id.map_root, MapsFragment.getInstance())
            fragmentTransaction.commit()

    }


   fun setTitle(title:String="Weather App"){
       binding.toolbar.title = title
   }



}