package com.mobiquity.network.model

data class Weather(
    val description: String,
    val icon: String,
    val id: Int,
    val main: String
)