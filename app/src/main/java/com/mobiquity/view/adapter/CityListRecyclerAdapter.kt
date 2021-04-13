package com.mobiquity.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mobiquity.databinding.CityListItemBinding
import com.mobiquity.view.model.CityModel
import com.mobiquity.viewmodel.MainViewmodel

class CityListRecyclerAdapter (var context: Context?,var viewmodel:MainViewmodel,var cityList: ArrayList<CityModel>): RecyclerView.Adapter<CityListRecyclerAdapter.CityViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityViewHolder {
        val binding = CityListItemBinding.inflate(LayoutInflater.from(context), parent, false)
        val holder = CityViewHolder(binding)
        return holder
    }

    override fun getItemCount(): Int {
        return cityList.size
    }

    override fun onBindViewHolder(holder: CityViewHolder, position: Int) {

        if(cityList[position] != null && position < cityList.size){
            val city = cityList[position]

            holder!!.bind(city,position)
        }
    }

    inner class CityViewHolder(var binding: CityListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(city: CityModel,position: Int){
            binding.city = city
            binding.viewmodel = viewmodel
            binding.position = position

        }


    }
}