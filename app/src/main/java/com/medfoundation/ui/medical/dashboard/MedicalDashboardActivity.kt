package com.medfoundation.ui.medical.dashboard

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.medfoundation.R
import com.medfoundation.databinding.ActivityMedicalDashboardBinding

class MedicalDashboardActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMedicalDashboardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMedicalDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment_medical) as NavHostFragment
        val navController = navHostFragment.navController
        binding.navViewMedical.setupWithNavController(navController)
    }
}
