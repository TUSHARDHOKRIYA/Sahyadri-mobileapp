package com.medfoundation.utils

data class BillSummary(
    val totalAmount: Double,
    val discountPercent: Int,
    val discountAmount: Double,
    val finalAmount: Double
)

object BillCalculator {
    fun calculateBill(totalAmount: Double): BillSummary {
        val discountPercent = 25
        val discountAmount = totalAmount * 0.25
        val finalAmount = totalAmount - discountAmount

        return BillSummary(
            totalAmount = totalAmount,
            discountPercent = discountPercent,
            discountAmount = discountAmount,
            finalAmount = finalAmount
        )
    }
}
