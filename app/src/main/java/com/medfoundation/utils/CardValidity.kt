package com.medfoundation.utils

import java.util.*

object CardValidity {
    fun calculateCardValidity(creationDate: Date): Pair<String, String> {
        val calendar = Calendar.getInstance()
        calendar.time = creationDate
        val month = calendar.get(Calendar.MONTH) // 0-indexed
        val year = calendar.get(Calendar.YEAR)

        val fyStartYear = if (month >= 3) year else year - 1
        val fyEndYear = fyStartYear + 1

        return Pair("1 Apr $fyStartYear", "31 Mar $fyEndYear")
    }

    fun getCardStatus(validTill: Date): String {
        val today = Calendar.getInstance().time
        val diffInMillies = validTill.time - today.time
        val diffInDays = diffInMillies / (1000 * 60 * 60 * 24)

        return when {
            diffInDays < 0 -> "EXPIRED"
            diffInDays <= 30 -> "EXPIRING SOON"
            else -> "ACTIVE"
        }
    }
}
