package com.medfoundation.ui.roleselection

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.medfoundation.databinding.ActivityRoleSelectionBinding
import com.medfoundation.ui.admin.login.AdminLoginActivity
import com.medfoundation.ui.medical.login.MedicalLoginActivity
import com.medfoundation.ui.patient.login.PatientLoginActivity

class RoleSelectionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRoleSelectionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRoleSelectionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.patientCard.setOnClickListener {
            startActivity(Intent(this, PatientLoginActivity::class.java))
        }

        binding.medicalCard.setOnClickListener {
            startActivity(Intent(this, MedicalLoginActivity::class.java))
        }

        binding.adminCard.setOnClickListener {
            startActivity(Intent(this, AdminLoginActivity::class.java))
        }
    }
}
