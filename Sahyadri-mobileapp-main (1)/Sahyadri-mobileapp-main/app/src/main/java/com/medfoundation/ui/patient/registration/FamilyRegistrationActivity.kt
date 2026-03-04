package com.medfoundation.ui.patient.registration

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.medfoundation.databinding.ActivityFamilyRegistrationBinding

class FamilyRegistrationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFamilyRegistrationBinding
    private var currentStep = 1
    private var membersAdded = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFamilyRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupDropdowns()

        binding.useCurrentLocationBtn.setOnClickListener {
            binding.headAddressInput.setText("Shivaji Nagar, Pune")
        }

        binding.addMemberBtn.setOnClickListener {
            if (membersAdded < 4) {
                membersAdded++
                binding.memberCountText.text = "$membersAdded/4 Members Added"
                Toast.makeText(this, "Member added successfully", Toast.LENGTH_SHORT).show()
                if (membersAdded == 4) {
                    binding.addMemberBtn.isEnabled = false
                }
            }
        }

        binding.nextButton.setOnClickListener {
            when (currentStep) {
                1 -> showStep(2, "Step 2: Add Members")
                2 -> showStep(3, "Step 3: Health Info")
                3 -> showStep(4, "Step 4: Review & Submit")
                4 -> submitRegistration()
            }
        }
    }

    private fun setupDropdowns() {
        val genders = arrayOf("Male", "Female", "Other")
        binding.genderDropdown.setAdapter(ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, genders))

        val relations = arrayOf("Self", "Spouse", "Child", "Parent")
        binding.relationDropdown.setAdapter(ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, relations))

        val bloodGroups = arrayOf("A+", "A-", "B+", "B-", "O+", "O-", "AB+", "AB-")
        binding.bloodGroupDropdown.setAdapter(ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, bloodGroups))
    }

    private fun showStep(step: Int, title: String) {
        currentStep = step
        binding.stepTitle.text = title
        binding.step1Container.visibility = if (step == 1) View.VISIBLE else View.GONE
        binding.step2Container.visibility = if (step == 2) View.VISIBLE else View.GONE
        binding.step3Container.visibility = if (step == 3) View.VISIBLE else View.GONE
        binding.step4Container.visibility = if (step == 4) View.VISIBLE else View.GONE

        if (step == 4) {
            binding.nextButton.text = "Submit for Approval"
            binding.summaryText.text = "Review all details for ${binding.headNameInput.text}. $membersAdded members added."
        } else {
            binding.nextButton.text = "Next"
        }
    }

    private fun submitRegistration() {
        AlertDialog.Builder(this)
            .setTitle("Registration Submitted")
            .setMessage("Your Smart Card will be activated within 24 hours.")
            .setPositiveButton("OK") { _, _ -> finish() }
            .show()
    }
}
