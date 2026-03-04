package com.medfoundation.model

data class Transaction(
    val billNo: String,
    val patientName: String,
    val cardId: String,
    val medicalShopName: String,
    val date: String,
    val totalAmount: Double,
    val type: String = "Medicine", // Medicine, IPD, Blood Bag, Full Body Checkup
    val discountPercent: Int = 25,
    val discountAmount: Double = totalAmount * (discountPercent / 100.0),
    val paidAmount: Double = totalAmount - discountAmount,
    val status: String = "PAID"
)
