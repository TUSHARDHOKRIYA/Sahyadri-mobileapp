package com.medfoundation.ui.patient.history

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.medfoundation.databinding.ItemTransactionBinding
import com.medfoundation.model.Transaction

class HistoryAdapter(private val transactions: List<Transaction>) :
    RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {

    class ViewHolder(val binding: ItemTransactionBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemTransactionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val transaction = transactions[position]
        holder.binding.shopName.text = transaction.medicalShopName
        holder.binding.date.text = transaction.date
        holder.binding.amount.text = "₹${transaction.paidAmount}"
        holder.binding.patientName.text = "For: ${transaction.patientName}"
        holder.binding.cardIdText.text = "Card ID: ${transaction.cardId}"

        // Set detailed amounts
        holder.binding.totalAmountDetail.text = "₹${String.format("%.2f", transaction.totalAmount)}"
        holder.binding.discountAmountDetail.text = "-₹${String.format("%.2f", transaction.discountAmount)}"
        holder.binding.paidAmountDetail.text = "₹${String.format("%.2f", transaction.paidAmount)}"

        holder.itemView.setOnClickListener {
            if (holder.binding.detailContainer.visibility == View.VISIBLE) {
                holder.binding.detailContainer.visibility = View.GONE
            } else {
                holder.binding.detailContainer.visibility = View.VISIBLE
            }
        }
    }

    override fun getItemCount() = transactions.size
}
