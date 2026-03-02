package com.medfoundation.ui.medical.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.medfoundation.data.DummyData
import com.medfoundation.databinding.FragmentMedicalHistoryBinding
import com.medfoundation.ui.patient.history.HistoryAdapter

class MedicalHistoryFragment : Fragment() {

    private var _binding: FragmentMedicalHistoryBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMedicalHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        binding.medicalHistoryRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        // Filter transactions for this medical shop (MedPlus Pharmacy in this demo)
        val filteredList = DummyData.dummyTransactions.filter { it.medicalShopName == "MedPlus Pharmacy" }.reversed()
        binding.medicalHistoryRecyclerView.adapter = HistoryAdapter(filteredList)
        
        val totalPaid = filteredList.sumOf { it.paidAmount }
        binding.dailyTotalText.text = "Shop Total: ₹${String.format("%.2f", totalPaid)}"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
