package com.medfoundation.ui.medical.billing

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.medfoundation.data.DummyData
import com.medfoundation.databinding.ActivityBillPreviewBinding
import com.medfoundation.model.Transaction
import com.medfoundation.utils.BillCalculator
import java.text.SimpleDateFormat
import java.util.*

class BillPreviewActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBillPreviewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBillPreviewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val memberName = intent.getStringExtra("MEMBER_NAME") ?: "Unknown"
        val totalAmount = intent.getDoubleExtra("TOTAL_AMOUNT", 0.0)
        val cardId = intent.getStringExtra("CARD_ID") ?: "MEDF-2025-00142"
        val summary = BillCalculator.calculateBill(totalAmount)

        val sdf = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
        val currentDate = sdf.format(Date())
        val billNo = "MED-MP-${System.currentTimeMillis() / 10000}"

        binding.billNoText.text = billNo
        binding.dateText.text = currentDate
        binding.patientNameText.text = memberName
        binding.totalAmountText.text = "₹ ${String.format("%.2f", summary.totalAmount)}"
        binding.discountAmountText.text = "- ₹ ${String.format("%.2f", summary.discountAmount)}"
        binding.payableAmountText.text = "₹ ${String.format("%.2f", summary.finalAmount)}"

        binding.shareBillBtn.setOnClickListener {
            Toast.makeText(this, "Bill shared successfully!", Toast.LENGTH_SHORT).show()
        }

        binding.doneBtn.setOnClickListener {
            // Add to dummy transactions in memory for the session
            DummyData.dummyTransactions.add(
                Transaction(
                    billNo = billNo,
                    patientName = memberName,
                    cardId = cardId,
                    medicalShopName = "MedPlus Pharmacy",
                    date = currentDate,
                    totalAmount = totalAmount
                )
            )
            Toast.makeText(this, "Transaction completed", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}
