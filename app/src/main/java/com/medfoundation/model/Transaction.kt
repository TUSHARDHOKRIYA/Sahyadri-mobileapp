package com.medfoundation.model

data class Transaction(
    val billNo: String,
    val patientName: String,
    val medicalShopName: String,
    val date: String,
    val totalAmount: Double,
    val discountAmount: Double = totalAmount * 0.25,
    val paidAmount: Double = totalAmount - discountAmount,
    val status: String = "PAID"
)
