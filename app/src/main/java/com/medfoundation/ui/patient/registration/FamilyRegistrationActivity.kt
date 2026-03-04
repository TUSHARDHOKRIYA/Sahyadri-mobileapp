package com.medfoundation.ui.patient.registration

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.medfoundation.databinding.ActivityFamilyRegistrationBinding

class FamilyRegistrationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFamilyRegistrationBinding
    private var currentStep = 1
    private var extraMembersAdded = 0 // Members added in Step 2 (excluding the Head)
    private val maxTotalMembers = 4 // Total capacity (Head + 3 members)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFamilyRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupDropdowns()
        setupBackNavigation()
        updateMemberCountDisplay()

        binding.useCurrentLocationBtn.setOnClickListener {
            // Conceptually taking the live current address
            binding.headAddressInput.setText("12, Shivaji Nagar, Pune (Live Location Verified)")
        }

        binding.addMemberBtn.setOnClickListener {
            if (validateMemberInput()) {
                if (extraMembersAdded < (maxTotalMembers - 1)) {
                    extraMembersAdded++
                    updateMemberCountDisplay()
                    clearMemberInputs()
                    Toast.makeText(this, "Member added successfully", Toast.LENGTH_SHORT).show()
                    
                    if (extraMembersAdded == (maxTotalMembers - 1)) {
                        binding.addMemberBtn.isEnabled = false
                        binding.addMemberBtn.text = "Maximum (4/4) Reached"
                    }
                }
            }
        }

        binding.nextButton.setOnClickListener {
            when (currentStep) {
                1 -> if (validateHeadInput()) showStep(2, "Step 2: Add Family Members")
                2 -> showStep(3, "Step 3: Review & Submit")
                3 -> showStep(4, "Step 4: Payment Gateway")
                4 -> processPayment()
            }
        }
    }

    private fun updateMemberCountDisplay() {
        binding.memberCountText.text = "${extraMembersAdded + 1}/$maxTotalMembers Members Added (Including Head)"
    }

    private fun setupBackNavigation() {
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (currentStep > 1) {
                    val prevStep = currentStep - 1
                    val prevTitle = when(prevStep) {
                        1 -> "Step 1: Head of Family"
                        2 -> "Step 2: Add Family Members"
                        3 -> "Step 3: Review & Submit"
                        else -> ""
                    }
                    showStep(prevStep, prevTitle)
                } else {
                    finish() // Exit registration to login page
                }
            }
        })
    }

    private fun setupDropdowns() {
        val genders = arrayOf("Male", "Female", "Other")
        val genderAdapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, genders)
        binding.headGenderDropdown.setAdapter(genderAdapter)
        binding.genderDropdown.setAdapter(genderAdapter)

        val relations = arrayOf("Spouse", "Child", "Parent", "Sibling")
        binding.relationDropdown.setAdapter(ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, relations))

        val bloodGroups = arrayOf("A+", "A-", "B+", "B-", "O+", "O-", "AB+", "AB-", "Not Known")
        val bloodAdapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, bloodGroups)
        binding.headBloodGroupDropdown.setAdapter(bloodAdapter)
        binding.memberBloodGroupDropdown.setAdapter(bloodAdapter)
    }

    private fun validateHeadInput(): Boolean {
        // Name, Age, Gender, Aadhaar, Mobile, Address are mandatory. Blood group is optional.
        if (binding.headNameInput.text.isNullOrBlank() || 
            binding.headAgeInput.text.isNullOrBlank() ||
            binding.headGenderDropdown.text.isNullOrBlank() ||
            binding.headAadhaarInput.text.isNullOrBlank() ||
            binding.headMobileInput.text.isNullOrBlank() ||
            binding.headAddressInput.text.isNullOrBlank()) {
            Toast.makeText(this, "All fields except Blood Group are mandatory", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    private fun validateMemberInput(): Boolean {
        // Name, Age, Gender, Aadhaar, Relationship are mandatory. Blood group is optional.
        if (binding.memberNameInput.text.isNullOrBlank() || 
            binding.memberAgeInput.text.isNullOrBlank() ||
            binding.genderDropdown.text.isNullOrBlank() ||
            binding.memberAadhaarInput.text.isNullOrBlank() ||
            binding.relationDropdown.text.isNullOrBlank()) {
            Toast.makeText(this, "Please fill all mandatory member details", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    private fun clearMemberInputs() {
        binding.memberNameInput.text = null
        binding.memberAgeInput.text = null
        binding.genderDropdown.text = null
        binding.memberAadhaarInput.text = null
        binding.relationDropdown.text = null
        binding.memberBloodGroupDropdown.text = null
    }

    private fun showStep(step: Int, title: String) {
        currentStep = step
        binding.stepTitle.text = title
        binding.step1Container.visibility = if (step == 1) View.VISIBLE else View.GONE
        binding.step2Container.visibility = if (step == 2) View.VISIBLE else View.GONE
        binding.step4Container.visibility = if (step == 3) View.VISIBLE else View.GONE
        binding.paymentContainer.visibility = if (step == 4) View.VISIBLE else View.GONE

        when (step) {
            3 -> {
                binding.nextButton.text = "Proceed to Payment"
                binding.summaryText.text = "Ready to register '${binding.headNameInput.text}' family with ${extraMembersAdded + 1} total members."
            }
            4 -> {
                binding.nextButton.text = "Pay ₹100 & Generate Card"
            }
            else -> {
                binding.nextButton.text = "Next"
            }
        }
    }

    private fun processPayment() {
        // Simulated Payment Success
        AlertDialog.Builder(this)
            .setTitle("Payment Successful ✅")
            .setMessage("Your transaction was successful. Generating your Smart Card...")
            .setPositiveButton("Continue") { _, _ -> showFinalSuccess() }
            .setCancelable(false)
            .show()
    }

    private fun showFinalSuccess() {
        AlertDialog.Builder(this)
            .setTitle("Smart Card Generated! 🏥")
            .setMessage("Your MedFoundation Smart Card is now active.\n\nPlease login with your mobile number: ${binding.headMobileInput.text} to view your card.")
            .setPositiveButton("Back to Login") { _, _ -> finish() }
            .setCancelable(false)
            .show()
    }
}
