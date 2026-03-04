package com.medfoundation.model

data class Medical(
    val name: String,
    val address: String,
    val latitude: Double,
    val longitude: Double,
    val rating: Double,
    val status: String
)
