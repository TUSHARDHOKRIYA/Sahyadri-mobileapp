package com.medfoundation.ui.medical.billing

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.medfoundation.data.DummyData
import com.medfoundation.databinding.ActivityCreateBillBinding
import com.medfoundation.utils.BillCalculator

class CreateBillActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCreateBillBinding
    private var currentAmount: Double = 0.0
    private var selectedImageUri: Uri? = null
    private var cardId: String = ""

    private val pickImageLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            selectedImageUri = result.data?.data
            binding.billImagePreview.setImageURI(selectedImageUri)
            binding.billImagePreview.alpha = 1.0f
        }
    }

    private val takePhotoLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val imageBitmap = result.data?.extras?.get("data") as? android.graphics.Bitmap
            binding.billImagePreview.setImageBitmap(imageBitmap)
            binding.billImagePreview.alpha = 1.0f
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateBillBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val initialMember = intent.getStringExtra("MEMBER_NAME")
        cardId = intent.getStringExtra("CARD_ID") ?: "MEDF-2025-00142"
        
        binding.patientNameHeader.text = "Patient: $initialMember"

        setupMemberDropdown(initialMember)
        setupAmountWatcher()

        binding.uploadBillBtn.setOnClickListener {
            showImagePickerOptions()
        }

        binding.generateBillBtn.setOnClickListener {
            if (currentAmount > 0) {
                val intent = Intent(this, BillPreviewActivity::class.java)
                intent.putExtra("MEMBER_NAME", binding.memberDropdown.text.toString())
                intent.putExtra("TOTAL_AMOUNT", currentAmount)
                intent.putExtra("CARD_ID", cardId)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Please enter a valid amount", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun showImagePickerOptions() {
        val options = arrayOf("Take Photo", "Choose from Gallery")
        AlertDialog.Builder(this)
            .setTitle("Upload Bill Image")
            .setItems(options) { _, which ->
                when (which) {
                    0 -> {
                        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                        takePhotoLauncher.launch(intent)
                    }
                    1 -> {
                        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                        pickImageLauncher.launch(intent)
                    }
                }
            }
            .show()
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
                    currentAmount = try { amountStr.toDouble() } catch (e: Exception) { 0.0 }
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
