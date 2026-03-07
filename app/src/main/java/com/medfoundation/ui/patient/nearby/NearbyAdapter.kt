package com.medfoundation.ui.patient.nearby

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.medfoundation.R
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
        val ctx: Context = holder.binding.root.context

        holder.binding.medicalName.text = medical.name
        holder.binding.medicalAddress.text = medical.address
        // Show only numeric rating without the star (star is already in the XML)
        holder.binding.rating.text = medical.rating.toString()
        holder.binding.statusChip.text = medical.status

        // Color the status badge: green for Open, muted red for Closed
        if (medical.status.equals("Open", ignoreCase = true)) {
            holder.binding.statusChip.setTextColor(
                ContextCompat.getColor(ctx, R.color.secondary)
            )
            holder.binding.statusChip.setBackgroundColor(
                ContextCompat.getColor(ctx, R.color.secondary_light)
            )
        } else {
            holder.binding.statusChip.setTextColor(
                ContextCompat.getColor(ctx, R.color.error)
            )
            holder.binding.statusChip.setBackgroundColor(
                ContextCompat.getColor(ctx, R.color.card_red_bg)
            )
        }

        holder.binding.directionsButton.setOnClickListener {
            try {
                val uri = Uri.parse("geo:${medical.latitude},${medical.longitude}?q=${Uri.encode(medical.name)}")
                val mapIntent = Intent(Intent.ACTION_VIEW, uri)
                mapIntent.setPackage("com.google.android.apps.maps")
                if (mapIntent.resolveActivity(ctx.packageManager) != null) {
                    ctx.startActivity(mapIntent)
                } else {
                    // Fallback: open in browser
                    val browserUri = Uri.parse(
                        "https://www.google.com/maps/search/?api=1&query=${medical.latitude},${medical.longitude}"
                    )
                    ctx.startActivity(Intent(Intent.ACTION_VIEW, browserUri))
                }
            } catch (e: Exception) {
                Toast.makeText(ctx, "Could not open maps", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun getItemCount() = medicals.size
}
