package com.medfoundation.ui.medical.billing

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.medfoundation.data.DummyData
import com.medfoundation.databinding.ActivityCreateBillBinding
import com.medfoundation.utils.BillCalculator

class CreateBillActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCreateBillBinding
    private var currentAmount: Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateBillBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val initialMember = intent.getStringExtra("MEMBER_NAME")
        binding.patientNameHeader.text = "Patient: $initialMember"

        setupMemberDropdown(initialMember)
        setupAmountWatcher()

        binding.uploadPrescriptionBtn.setOnClickListener {
            Toast.makeText(this, "Prescription uploaded!", Toast.LENGTH_SHORT).show()
        }

        binding.generateBillBtn.setOnClickListener {
            if (currentAmount > 0) {
                val intent = Intent(this, BillPreviewActivity::class.java)
                intent.putExtra("MEMBER_NAME", binding.memberDropdown.text.toString())
                intent.putExtra("TOTAL_AMOUNT", currentAmount)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Please enter a valid amount", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setupMemberDropdown(initialMember: String?) {
        val members = DummyData.dummyMembers.map { it.name }
        val adapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, members)
        binding.memberDropdown.setAdapter(adapter)
        if (initialMember != null) {
            binding.memberDropdown.setText(initialMember, false)
        }
    }

    private fun setupAmountWatcher() {
        binding.amountInput.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val amountStr = s.toString()
                if (amountStr.isNotEmpty()) {
                    currentAmount = amountStr.toDouble()
                    val summary = BillCalculator.calculateBill(currentAmount)
                    binding.summaryTotal.text = "₹ ${String.format("%.2f", summary.totalAmount)}"
                    binding.summaryDiscount.text = "-₹ ${String.format("%.2f", summary.discountAmount)}"
                    binding.summaryFinal.text = "₹ ${String.format("%.2f", summary.finalAmount)}"
                } else {
                    currentAmount = 0.0
                    binding.summaryTotal.text = "₹ 0.00"
                    binding.summaryDiscount.text = "-₹ 0.00"
                    binding.summaryFinal.text = "₹ 0.00"
                }
            }
            override fun afterTextChanged(s: Editable?) {}
        })
    }
}
