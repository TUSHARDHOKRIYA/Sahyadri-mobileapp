package com.medfoundation.ui.patient.home

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.medfoundation.R
import com.medfoundation.data.DummyData
import com.medfoundation.databinding.FragmentHomeBinding
import com.medfoundation.utils.QRHelper

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    
    private lateinit var backPressedCallback: OnBackPressedCallback

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        setupGreeting()
        setupSavings()
        setupSmartCardOverlay()
        setupBenefitActions()
        
        // Add subtle entry animations
        val anim = AnimationUtils.loadAnimation(requireContext(), R.anim.fade_in_up)
        binding.mainScrollView.startAnimation(anim)

        // Setup back press handling for card overlay
        backPressedCallback = object : OnBackPressedCallback(false) {
            override fun handleOnBackPressed() {
                hideCardOverlay()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, backPressedCallback)
    }

    private fun setupGreeting() {
        binding.greetingText.text = "Hello, ${DummyData.dummyFamily.headName.split(" ")[0]} 👋"
    }

    private fun setupSavings() {
        val totalSaved = DummyData.dummyTransactions.sumOf { it.discountAmount }
        binding.totalSavedAmount.text = "₹ ${String.format("%,.2f", totalSaved)}"
    }

    private fun setupSmartCardOverlay() {
        val family = DummyData.dummyFamily
        
        // Setup the include layout within overlay
        val overlayCard = binding.cardOverlay.findViewById<View>(R.id.fullCardLayout)
        overlayCard.findViewById<TextView>(R.id.cardId).text = family.smartCardId
        overlayCard.findViewById<TextView>(R.id.headName).text = family.headName
        overlayCard.findViewById<TextView>(R.id.memberCount).text = "Members: ${DummyData.dummyMembers.size}"
        overlayCard.findViewById<TextView>(R.id.fyYear).text = "FY: ${family.financialYear}"
        overlayCard.findViewById<TextView>(R.id.validity).text = "${family.validFrom} – ${family.validTill}"
        
        val qrBitmap = QRHelper.generateQRCode(family.smartCardId)
        overlayCard.findViewById<android.widget.ImageView>(R.id.qrCode).setImageBitmap(qrBitmap)

        // Show overlay on blue box click
        binding.smartCardPreviewBox.setOnClickListener {
            showCardOverlay()
        }

        // Hide overlay on click anywhere
        binding.cardOverlay.setOnClickListener {
            hideCardOverlay()
        }
    }

    private fun showCardOverlay() {
        binding.cardOverlay.visibility = View.VISIBLE
        binding.cardOverlay.startAnimation(AnimationUtils.loadAnimation(context, R.anim.scale_up))
        backPressedCallback.isEnabled = true
    }

    private fun hideCardOverlay() {
        val anim = AnimationUtils.loadAnimation(context, R.anim.scale_down)
        anim.setAnimationListener(object : android.view.animation.Animation.AnimationListener {
            override fun onAnimationStart(p0: android.view.animation.Animation?) {}
            override fun onAnimationRepeat(p0: android.view.animation.Animation?) {}
            override fun onAnimationEnd(p0: android.view.animation.Animation?) {
                binding.cardOverlay.visibility = View.GONE
                backPressedCallback.isEnabled = false
            }
        })
        binding.cardOverlay.startAnimation(anim)
    }

    private fun setupBenefitActions() {
        binding.btnFindMedical.setOnClickListener { showLocationList("Partner Medicals") }
        binding.btnFindHospital.setOnClickListener { showLocationList("Partner Hospitals") }
        binding.btnFindLab.setOnClickListener { showLocationList("Partner Labs") }
        binding.btnFindLab2.setOnClickListener { showLocationList("Partner Labs") }
        binding.btnFindHospital2.setOnClickListener { showLocationList("Maternity Centers") }
        binding.btnFindBloodBank.setOnClickListener { showLocationList("Blood Banks") }
    }

    private fun showLocationList(title: String) {
        val locations = DummyData.dummyMedicals // Repurposing dummy data for simulation
        val locationNames = locations.map { "${it.name}\n${it.address}" }.toTypedArray()

        AlertDialog.Builder(requireContext())
            .setTitle(title)
            .setItems(locationNames) { _, which ->
                val selected = locations[which]
                showRouteDialog(selected.name, selected.latitude, selected.longitude)
            }
            .setPositiveButton("Close", null)
            .show()
    }

    private fun showRouteDialog(name: String, lat: Double, lng: Double) {
        AlertDialog.Builder(requireContext())
            .setTitle(name)
            .setMessage("Would you like to get directions to this location?")
            .setPositiveButton("Get Route") { _, _ ->
                val gmmIntentUri = Uri.parse("google.navigation:q=$lat,$lng")
                val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
                mapIntent.setPackage("com.google.android.apps.maps")
                if (mapIntent.resolveActivity(requireActivity().packageManager) != null) {
                    startActivity(mapIntent)
                } else {
                    Toast.makeText(context, "Google Maps not found", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
