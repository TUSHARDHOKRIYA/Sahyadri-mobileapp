package com.medfoundation.ui.patient.nearby

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.medfoundation.databinding.ItemMedicalBinding
import com.medfoundation.model.Medical

class NearbyAdapter(private val medicals: List<Medical>) :
    RecyclerView.Adapter<NearbyAdapter.ViewHolder>() {

    class ViewHolder(val binding: ItemMedicalBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemMedicalBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val medical = medicals[position]
        holder.binding.medicalName.text = medical.name
        holder.binding.medicalAddress.text = medical.address
        holder.binding.rating.text = "${medical.rating} ⭐"
        holder.binding.statusChip.text = medical.status

        holder.binding.directionsButton.setOnClickListener {
            // Open Google Maps (Prototype: show toast)
        }
    }

    override fun getItemCount() = medicals.size
}
