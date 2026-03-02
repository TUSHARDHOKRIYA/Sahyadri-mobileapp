package com.medfoundation.ui.medical.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.card.MaterialCardView
import com.medfoundation.R
import com.medfoundation.data.DummyData
import com.medfoundation.databinding.FragmentMedicalDashboardBinding

class MedicalDashboardFragment : Fragment() {

    private var _binding: FragmentMedicalDashboardBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMedicalDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.scanButton.setOnClickListener {
            findNavController().navigate(R.id.navigation_medical_scan)
        }

        setupRecentBills()
    }

    private fun setupRecentBills() {
        val transactions = DummyData.dummyTransactions.takeLast(3).reversed()
        binding.recentBillsContainer.removeAllViews()

        for (transaction in transactions) {
            val card = LayoutInflater.from(requireContext())
                .inflate(R.layout.item_transaction, binding.recentBillsContainer, false) as MaterialCardView
            
            card.findViewById<TextView>(R.id.shopName).text = transaction.billNo
            card.findViewById<TextView>(R.id.date).text = transaction.date
            card.findViewById<TextView>(R.id.amount).text = "₹${transaction.paidAmount}"
            card.findViewById<TextView>(R.id.patientName).text = "Patient: ${transaction.patientName}"
            
            binding.recentBillsContainer.addView(card)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
