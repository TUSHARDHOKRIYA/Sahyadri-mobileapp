package com.medfoundation.ui.admin.families

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView
import com.medfoundation.R
import com.medfoundation.data.DummyData
import com.medfoundation.databinding.FragmentAdminFamiliesBinding

class AdminFamiliesFragment : Fragment() {

    private var _binding: FragmentAdminFamiliesBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAdminFamiliesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupPendingFamilies()
        setupAllFamilies()
    }

    private fun setupPendingFamilies() {
        val families = listOf("Verma Family", "Patil Family", "Deshmukh Family")
        binding.pendingFamiliesContainer.removeAllViews()
        for (name in families) {
            val card = LayoutInflater.from(requireContext()).inflate(R.layout.item_pending_family, binding.pendingFamiliesContainer, false) as MaterialCardView
            card.findViewById<TextView>(R.id.familyName).text = name
            card.findViewById<MaterialButton>(R.id.approveBtn).setOnClickListener {
                Toast.makeText(requireContext(), "Approved $name", Toast.LENGTH_SHORT).show()
            }
            card.findViewById<MaterialButton>(R.id.rejectBtn).setOnClickListener {
                Toast.makeText(requireContext(), "Rejected $name", Toast.LENGTH_SHORT).show()
            }
            binding.pendingFamiliesContainer.addView(card)
        }
    }

    private fun setupAllFamilies() {
        val family = DummyData.dummyFamily
        binding.allFamiliesContainer.removeAllViews()
        val card = LayoutInflater.from(requireContext()).inflate(R.layout.item_family_card, binding.allFamiliesContainer, false) as MaterialCardView
        card.findViewById<TextView>(R.id.familyName).text = "Sharma Family"
        card.findViewById<TextView>(R.id.cardId).text = family.smartCardId
        card.findViewById<TextView>(R.id.memberCount).text = "4 Members"
        card.setOnClickListener {
            Toast.makeText(requireContext(), "Opening Family Detail...", Toast.LENGTH_SHORT).show()
        }
        binding.allFamiliesContainer.addView(card)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
