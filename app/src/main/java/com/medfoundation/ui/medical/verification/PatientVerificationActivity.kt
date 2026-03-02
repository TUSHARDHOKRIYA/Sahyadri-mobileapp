package com.medfoundation.ui.medical.verification

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.card.MaterialCardView
import com.google.android.material.chip.Chip
import com.google.android.material.tabs.TabLayout
import com.medfoundation.R
import com.medfoundation.data.DummyData
import com.medfoundation.databinding.ActivityPatientVerificationBinding
import com.medfoundation.model.Member
import com.medfoundation.ui.medical.billing.CreateBillActivity

class PatientVerificationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPatientVerificationBinding
    private var selectedMember: Member? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPatientVerificationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupTabs()
        setupHistory()

        binding.createBillFab.setOnClickListener {
            val intent = Intent(this, CreateBillActivity::class.java)
            intent.putExtra("MEMBER_NAME", selectedMember?.name ?: DummyData.dummyMembers[0].name)
            startActivity(intent)
        }
    }

    private fun setupTabs() {
        val members = DummyData.dummyMembers
        for (member in members) {
            binding.memberTabs.addTab(binding.memberTabs.newTab().setText(member.name))
        }

        binding.memberTabs.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                val index = tab?.position ?: 0
                updateMemberInfo(members[index])
            }
            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })

        updateMemberInfo(members[0])
    }

    private fun updateMemberInfo(member: Member) {
        selectedMember = member
        binding.memberNameText.text = member.name
        binding.memberDetailText.text = "${member.age} | ${member.gender} | ${member.relationship}"
        binding.memberAadhaarText.text = "Aadhaar: XXXX-XXXX-3210"
        
        binding.healthChips.removeAllViews()
        addChip(member.bloodGroup)
        member.chronicConditions.split(",").forEach { addChip(it.trim()) }
    }

    private fun addChip(text: String) {
        if (text == "None") return
        val chip = Chip(this)
        chip.text = text
        binding.healthChips.addView(chip)
    }

    private fun setupHistory() {
        val transactions = DummyData.dummyTransactions.takeLast(5).reversed()
        binding.historyList.removeAllViews()
        for (transaction in transactions) {
            val card = LayoutInflater.from(this).inflate(R.layout.item_transaction, binding.historyList, false) as MaterialCardView
            card.findViewById<TextView>(R.id.shopName).text = transaction.medicalShopName
            card.findViewById<TextView>(R.id.date).text = transaction.date
            card.findViewById<TextView>(R.id.amount).text = "₹${transaction.paidAmount}"
            card.findViewById<TextView>(R.id.patientName).text = "For: ${transaction.patientName}"
            binding.historyList.addView(card)
        }
    }
}
