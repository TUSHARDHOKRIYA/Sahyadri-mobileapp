package com.medfoundation.ui.patient.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.card.MaterialCardView
import com.google.android.material.textfield.TextInputEditText
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

        setupProfileInfo()
        setupMembersList()

        binding.btnLogout.setOnClickListener {
            startActivity(Intent(requireContext(), RoleSelectionActivity::class.java))
            requireActivity().finish()
        }

        binding.btnEditProfile.setOnClickListener {
            showEditProfileDialog()
        }

        binding.btnGlobalEdit.setOnClickListener {
            showEditProfileDialog()
        }
    }

    private fun setupProfileInfo() {
        val family = DummyData.dummyFamily
        binding.headNameProfile.text = family.headName
        binding.cardIdProfile.text = "Smart Card ID: ${family.smartCardId}"
    }

    private fun setupMembersList() {
        val members = DummyData.dummyMembers
        binding.membersContainerProfile.removeAllViews()

        for (member in members) {
            val view = LayoutInflater.from(requireContext())
                .inflate(R.layout.item_member, binding.membersContainerProfile, false) as MaterialCardView
            view.findViewById<TextView>(R.id.memberName).text = member.name
            view.findViewById<TextView>(R.id.memberRelation).text =
                "${member.relationship}  ·  ${member.age} yrs  ·  ${member.gender}"

            val infoContainer = view.findViewById<View>(R.id.healthInfoContainer)
            val expandIcon = view.findViewById<ImageView>(R.id.expandIcon)

            view.findViewById<TextView>(R.id.bloodGroup).text = member.bloodGroup
            view.findViewById<TextView>(R.id.chronicConditions).visibility = View.GONE
            view.findViewById<TextView>(R.id.allergies).text = "XXXX-XXXX-3210"

            view.setOnClickListener {
                if (infoContainer.visibility == View.VISIBLE) {
                    infoContainer.visibility = View.GONE
                    expandIcon.setImageResource(android.R.drawable.arrow_down_float)
                } else {
                    infoContainer.visibility = View.VISIBLE
                    infoContainer.startAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_in))
                    expandIcon.setImageResource(android.R.drawable.arrow_up_float)
                }
            }
            binding.membersContainerProfile.addView(view)
        }
    }

    private fun showEditProfileDialog() {
        val family = DummyData.dummyFamily
        val dialog = BottomSheetDialog(requireContext())
        val sheetView = LayoutInflater.from(requireContext())
            .inflate(R.layout.dialog_edit_profile, null)

        // Pre-fill current values
        sheetView.findViewById<TextInputEditText>(R.id.editHeadName)
            .setText(family.headName)
        sheetView.findViewById<TextInputEditText>(R.id.editContactNo)
            .setText(family.contactNo)
        sheetView.findViewById<TextInputEditText>(R.id.editAddress)
            .setText(family.address)

        sheetView.findViewById<View>(R.id.cancelEditBtn).setOnClickListener {
            dialog.dismiss()
        }

        sheetView.findViewById<View>(R.id.saveEditBtn).setOnClickListener {
            val newName = sheetView.findViewById<TextInputEditText>(R.id.editHeadName)
                .text?.toString()?.trim() ?: ""
            val newContact = sheetView.findViewById<TextInputEditText>(R.id.editContactNo)
                .text?.toString()?.trim() ?: ""

            if (newName.isEmpty()) {
                sheetView.findViewById<TextInputEditText>(R.id.editHeadName).error = "Name is required"
                return@setOnClickListener
            }

            // Update display in profile header
            if (newName.isNotEmpty()) {
                binding.headNameProfile.text = newName
            }

            Toast.makeText(
                requireContext(),
                "Profile updated successfully!",
                Toast.LENGTH_SHORT
            ).show()
            dialog.dismiss()
        }

        dialog.setContentView(sheetView)
        dialog.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
