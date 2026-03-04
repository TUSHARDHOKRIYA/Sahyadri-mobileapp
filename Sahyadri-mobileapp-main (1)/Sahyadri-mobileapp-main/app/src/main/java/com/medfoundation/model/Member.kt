package com.medfoundation.model

data class Member(
    val name: String,
    val relationship: String,
    val age: Int,
    val gender: String,
    val bloodGroup: String,
    val chronicConditions: String,
    val allergies: String = "None",
    val currentMedicines: String = "None"
)
