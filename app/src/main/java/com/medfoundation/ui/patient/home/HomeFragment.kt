package com.medfoundation.ui.patient.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.android.material.card.MaterialCardView
import com.medfoundation.R
import com.medfoundation.data.DummyData
import com.medfoundation.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

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
        setupRecentTransactions()
    }

    private fun setupRecentTransactions() {
        val transactions = DummyData.dummyTransactions.takeLast(2).reversed()
        binding.recentTransactionsContainer.removeAllViews()

        for (transaction in transactions) {
            val card = LayoutInflater.from(requireContext())
                .inflate(R.layout.item_transaction, binding.recentTransactionsContainer, false) as MaterialCardView
            
            card.findViewById<TextView>(R.id.shopName).text = transaction.medicalShopName
            card.findViewById<TextView>(R.id.date).text = transaction.date
            card.findViewById<TextView>(R.id.amount).text = "₹${transaction.paidAmount}"
            card.findViewById<TextView>(R.id.patientName).text = "For: ${transaction.patientName}"
            
            binding.recentTransactionsContainer.addView(card)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
