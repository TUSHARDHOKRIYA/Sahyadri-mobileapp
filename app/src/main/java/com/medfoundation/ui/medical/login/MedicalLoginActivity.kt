package com.medfoundation.ui.medical.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.medfoundation.databinding.ActivityMedicalLoginBinding
import com.medfoundation.ui.medical.dashboard.MedicalDashboardActivity

class MedicalLoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMedicalLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMedicalLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.loginButton.setOnClickListener {
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()

            if (email == "medical@medfound.com" && password == "med123") {
                startActivity(Intent(this, MedicalDashboardActivity::class.java))
                finish()
            } else {
                Toast.makeText(this, "Invalid credentials. Use medical@medfound.com / med123", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
