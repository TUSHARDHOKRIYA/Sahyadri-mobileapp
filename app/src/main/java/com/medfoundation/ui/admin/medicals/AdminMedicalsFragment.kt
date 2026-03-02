package com.medfoundation.ui.admin.medicals

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.medfoundation.data.DummyData
import com.medfoundation.databinding.FragmentAdminMedicalsBinding
import com.medfoundation.ui.patient.nearby.NearbyAdapter

class AdminMedicalsFragment : Fragment() {

    private var _binding: FragmentAdminMedicalsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAdminMedicalsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        
        binding.addMedicalFab.setOnClickListener {
            Toast.makeText(requireContext(), "Add Medical form coming soon!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupRecyclerView() {
        binding.adminMedicalsRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.adminMedicalsRecyclerView.adapter = NearbyAdapter(DummyData.dummyMedicals)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
