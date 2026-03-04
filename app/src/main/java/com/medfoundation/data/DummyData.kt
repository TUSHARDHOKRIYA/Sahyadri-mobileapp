package com.medfoundation.data

import com.medfoundation.model.*

object DummyData {
    val dummyFamily = Family(
        smartCardId = "MEDF-2025-00142",
        headName = "Rajesh Sharma",
        contactNo = "9876543210",
        address = "12, Shivaji Nagar, Pune",
        financialYear = "FY 2025-26",
        validFrom = "1 Apr 2025",
        validTill = "31 Mar 2026",
        cardStatus = "ACTIVE"
    )

    val dummyMembers = mutableListOf(
        Member("Rajesh Sharma", "Self", 45, "Male", "A+", "Diabetes"),
        Member("Sunita Sharma", "Spouse", 41, "Female", "B+", "Thyroid"),
        Member("Aman Sharma", "Child", 18, "Male", "O+", "None"),
        Member("Kamla Sharma", "Parent", 70, "Female", "A+", "BP, Arthritis")
    )

    val dummyTransactions = mutableListOf(
        Transaction("BILL-001", "Rajesh Sharma", "MEDF-2025-00142", "MedPlus Pharmacy", "12 Jan 2025", 1200.0, "Medicine", 25),
        Transaction("BILL-002", "Sunita Sharma", "MEDF-2025-00142", "Apollo Pharmacy", "28 Jan 2025", 850.0, "Medicine", 25),
        Transaction("BILL-003", "Kamla Sharma", "MEDF-2025-00142", "Sahyadri Hospital", "05 Feb 2025", 15000.0, "IPD", 20),
        Transaction("BILL-004", "Rajesh Sharma", "MEDF-2025-00142", "Jan Aushadhi Store", "14 Feb 2025", 600.0, "Medicine", 25),
        Transaction("BILL-005", "Aman Sharma", "MEDF-2025-00142", "PathLab Pro", "01 Mar 2025", 4500.0, "Full Body Checkup", 51) // Flat price logic simulated by %
    )

    val dummyMedicals = listOf(
        Medical("MedPlus Pharmacy", "Camp Road, Pune", 18.5204, 73.8567, 4.5, "Open"),
        Medical("Apollo Pharmacy", "FC Road, Pune", 18.5314, 73.8446, 4.3, "Open"),
        Medical("Jan Aushadhi Store", "Kothrud, Pune", 18.5089, 73.8259, 4.1, "Closed"),
        Medical("Sahyadri Medical", "Shivajinagar, Pune", 18.5308, 73.8474, 4.7, "Open"),
        Medical("LifeCare Pharmacy", "Deccan, Pune", 18.5167, 73.8397, 4.0, "Open")
    )

    val dummyAdminStats = AdminStats(
        totalFamilies = 1284,
        totalPatients = 3891,
        totalTransactionsToday = 47,
        totalDiscountGiven = 284500.0,
        activeMedicals = 38,
        pendingApprovals = 12
    )
}
