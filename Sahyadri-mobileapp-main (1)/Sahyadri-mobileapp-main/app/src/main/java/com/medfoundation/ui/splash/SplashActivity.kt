package com.medfoundation.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.AlphaAnimation
import androidx.appcompat.app.AppCompatActivity
import com.medfoundation.databinding.ActivitySplashBinding
import com.medfoundation.ui.roleselection.RoleSelectionActivity

class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val fadeIn = AlphaAnimation(0f, 1f)
        fadeIn.duration = 1500
        binding.logo.startAnimation(fadeIn)
        binding.appName.startAnimation(fadeIn)

        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, RoleSelectionActivity::class.java))
            finish()
        }, 2000)
    }
}
