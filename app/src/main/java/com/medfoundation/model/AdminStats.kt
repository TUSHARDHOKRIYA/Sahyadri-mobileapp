package com.medfoundation.model

data class AdminStats(
    val totalFamilies: Int,
    val totalPatients: Int,
    val totalTransactionsToday: Int,
    val totalDiscountGiven: Double,
    val activeMedicals: Int,
    val pendingApprovals: Int
)
