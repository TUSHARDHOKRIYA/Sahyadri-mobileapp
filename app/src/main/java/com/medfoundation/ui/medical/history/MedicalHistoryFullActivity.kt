package com.medfoundation.ui.medical.history

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.medfoundation.data.DummyData
import com.medfoundation.databinding.ActivityMedicalHistoryFullBinding
import com.medfoundation.ui.patient.history.HistoryAdapter

class MedicalHistoryFullActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMedicalHistoryFullBinding
    private lateinit var adapter: HistoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMedicalHistoryFullBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupToolbar()
        setupFilters()
        setupRecyclerView()
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        binding.toolbar.setNavigationOnClickListener { finish() }
    }

    private fun setupFilters() {
        val months = arrayOf("All Months", "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December")
        val years = arrayOf("2025", "2024")

        binding.monthFilter.setAdapter(ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, months))
        binding.yearFilter.setAdapter(ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, years))

        binding.monthFilter.setText(months[0], false)
        binding.yearFilter.setText(years[0], false)

        binding.monthFilter.setOnItemClickListener { _, _, _, _ -> applyFilters() }
        binding.yearFilter.setOnItemClickListener { _, _, _, _ -> applyFilters() }
    }

    private fun setupRecyclerView() {
        binding.fullHistoryRecyclerView.layoutManager = LinearLayoutManager(this)
        adapter = HistoryAdapter(DummyData.dummyTransactions.filter { it.medicalShopName == "MedPlus Pharmacy" }.reversed())
        binding.fullHistoryRecyclerView.adapter = adapter
    }

    private fun applyFilters() {
        val month = binding.monthFilter.text.toString()
        val year = binding.yearFilter.text.toString()

        val filtered = DummyData.dummyTransactions.filter { 
            it.medicalShopName == "MedPlus Pharmacy" &&
            (month == "All Months" || it.date.contains(month.substring(0, 3))) &&
            it.date.contains(year)
        }.reversed()

        adapter = HistoryAdapter(filtered)
        binding.fullHistoryRecyclerView.adapter = adapter

        if (filtered.isEmpty()) {
            binding.emptyView.visibility = View.VISIBLE
            binding.fullHistoryRecyclerView.visibility = View.GONE
        } else {
            binding.emptyView.visibility = View.GONE
            binding.fullHistoryRecyclerView.visibility = View.VISIBLE
        }
    }
}
