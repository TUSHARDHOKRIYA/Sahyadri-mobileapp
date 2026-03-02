package com.medfoundation.ui.patient.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.android.material.card.MaterialCardView
import com.medfoundation.R
import com.medfoundation.data.DummyData
import com.medfoundation.databinding.FragmentProfileBinding
import com.medfoundation.ui.roleselection.RoleSelectionActivity

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupHeadInfo()
        setupMembers()

        binding.logoutButton.setOnClickListener {
            startActivity(Intent(requireContext(), RoleSelectionActivity::class.java))
            requireActivity().finish()
        }
    }

    private fun setupHeadInfo() {
        val family = DummyData.dummyFamily
        binding.headName.text = family.headName
        binding.contactNo.text = "+91 ${family.contactNo}"
        binding.address.text = family.address
    }

    private fun setupMembers() {
        val members = DummyData.dummyMembers
        binding.membersContainer.removeAllViews()
        for (member in members) {
            val view = LayoutInflater.from(requireContext()).inflate(R.layout.item_member, binding.membersContainer, false) as MaterialCardView
            view.findViewById<TextView>(R.id.memberName).text = member.name
            view.findViewById<TextView>(R.id.memberRelation).text = "${member.relationship} | ${member.age} | ${member.gender}"
            
            val infoContainer = view.findViewById<View>(R.id.healthInfoContainer)
            val expandIcon = view.findViewById<ImageView>(R.id.expandIcon)
            
            view.findViewById<TextView>(R.id.bloodGroup).text = "Blood Group: ${member.bloodGroup}"
            view.findViewById<TextView>(R.id.chronicConditions).text = "Chronic Conditions: ${member.chronicConditions}"
            view.findViewById<TextView>(R.id.allergies).text = "Allergies: ${member.allergies}"

            view.setOnClickListener {
                if (infoContainer.visibility == View.VISIBLE) {
                    infoContainer.visibility = View.GONE
                    expandIcon.setImageResource(android.R.drawable.arrow_down_float)
                } else {
                    infoContainer.visibility = View.VISIBLE
                    expandIcon.setImageResource(android.R.drawable.arrow_up_float)
                }
            }
            binding.membersContainer.addView(view)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
