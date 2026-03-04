package com.medfoundation.ui.patient.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.medfoundation.databinding.ActivityPatientLoginBinding
import com.medfoundation.ui.patient.dashboard.PatientDashboardActivity
import com.medfoundation.ui.patient.registration.FamilyRegistrationActivity

class PatientLoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPatientLoginBinding
    private val hardcodedOtp = "1234"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPatientLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.sendOtpButton.setOnClickListener {
            val mobile = binding.mobileEditText.text.toString()
            if (mobile.length == 10) {
                binding.otpInputLayout.visibility = View.VISIBLE
                binding.loginButton.visibility = View.VISIBLE
                binding.sendOtpButton.visibility = View.GONE
                Toast.makeText(this, "OTP sent to $mobile (Demo: 1234)", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Enter valid 10-digit mobile number", Toast.LENGTH_SHORT).show()
            }
        }

        binding.loginButton.setOnClickListener {
            val otp = binding.otpEditText.text.toString()
            if (otp == hardcodedOtp) {
                startActivity(Intent(this, PatientDashboardActivity::class.java))
                finish()
            } else {
                Toast.makeText(this, "Wrong OTP. Try 1234", Toast.LENGTH_SHORT).show()
            }
        }

        binding.registerLink.setOnClickListener {
            startActivity(Intent(this, FamilyRegistrationActivity::class.java))
        }
    }
}
