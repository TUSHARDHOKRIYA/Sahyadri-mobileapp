package com.medfoundation.ui.patient.smartcard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.medfoundation.data.DummyData
import com.medfoundation.databinding.FragmentSmartCardBinding
import com.medfoundation.utils.QRHelper

class SmartCardFragment : Fragment() {

    private var _binding: FragmentSmartCardBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSmartCardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupCard()

        binding.downloadButton.setOnClickListener {
            Toast.makeText(requireContext(), "Card saved to gallery!", Toast.LENGTH_SHORT).show()
        }

        binding.shareButton.setOnClickListener {
            Toast.makeText(requireContext(), "Sharing card...", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupCard() {
        val family = DummyData.dummyFamily
        binding.cardId.text = family.smartCardId
        binding.headName.text = family.headName
        binding.memberCount.text = "Members: ${DummyData.dummyMembers.size}"
        binding.fyYear.text = "FY: ${family.financialYear}"
        binding.validity.text = "${family.validFrom} – ${family.validTill}"

        val qrBitmap = QRHelper.generateQRCode(family.smartCardId)
        if (qrBitmap != null) {
            binding.qrCode.setImageBitmap(qrBitmap)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
