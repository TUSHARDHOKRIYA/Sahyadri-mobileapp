package com.medfoundation.model

data class Family(
    val smartCardId: String,
    val headName: String,
    val contactNo: String,
    val address: String,
    val financialYear: String,
    val validFrom: String,
    val validTill: String,
    val cardStatus: String
)
