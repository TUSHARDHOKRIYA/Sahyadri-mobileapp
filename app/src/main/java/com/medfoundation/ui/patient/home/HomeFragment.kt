package com.medfoundation.ui.patient.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import com.google.android.material.card.MaterialCardView
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
        setupRecentTransactions()
        setupLocationActions()
        
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

    private fun setupRecentTransactions() {
        val transactions = DummyData.dummyTransactions.takeLast(3).reversed()
        binding.recentTransactionsContainer.removeAllViews()

        for (transaction in transactions) {
            val card = LayoutInflater.from(requireContext())
                .inflate(R.layout.item_transaction, binding.recentTransactionsContainer, false) as MaterialCardView
            
            card.findViewById<TextView>(R.id.shopName).text = transaction.medicalShopName
            card.findViewById<TextView>(R.id.date).text = transaction.date
            card.findViewById<TextView>(R.id.amount).text = "₹${String.format("%.2f", transaction.paidAmount)}"
            card.findViewById<TextView>(R.id.patientName).text = "For: ${transaction.patientName}"
            card.findViewById<TextView>(R.id.cardIdText).text = "Type: ${transaction.type}"

            // Expansion details
            val detailContainer = card.findViewById<View>(R.id.detailContainer)
            card.findViewById<TextView>(R.id.totalAmountDetail).text = "₹${String.format("%.2f", transaction.totalAmount)}"
            card.findViewById<TextView>(R.id.discountAmountDetail).text = "-₹${String.format("%.2f", transaction.discountAmount)} (${transaction.discountPercent}%)"
            card.findViewById<TextView>(R.id.paidAmountDetail).text = "₹${String.format("%.2f", transaction.paidAmount)}"

            card.setOnClickListener {
                if (detailContainer.visibility == View.VISIBLE) {
                    detailContainer.visibility = View.GONE
                } else {
                    detailContainer.visibility = View.VISIBLE
                    detailContainer.startAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_in))
                }
            }
            
            binding.recentTransactionsContainer.addView(card)
        }
    }

    private fun setupLocationActions() {
        binding.btnUseCurrentLocation.setOnClickListener {
            binding.nearestMedicalName.text = "Sahyadri Medical (Closest)"
            binding.nearestMedicalDist.text = "Deccan, Pune | 0.4 km"
            Toast.makeText(context, "Location updated!", Toast.LENGTH_SHORT).show()
        }

        binding.btnGetDirections.setOnClickListener {
            Toast.makeText(context, "Opening Google Maps Directions...", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
