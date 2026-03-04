package com.medfoundation.ui.medical.history

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.medfoundation.data.DummyData
import com.medfoundation.databinding.FragmentMedicalHistoryBinding

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
        setupInsights()
        setupChart()

        binding.viewHistoryBtn.setOnClickListener {
            val intent = Intent(requireContext(), MedicalHistoryFullActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setupInsights() {
        val filteredList = DummyData.dummyTransactions.filter { it.medicalShopName == "MedPlus Pharmacy" }
        val totalLifetime = filteredList.sumOf { it.paidAmount }
        
        binding.totalSalesText.text = "₹ ${String.format("%,.0f", totalLifetime)}"
        binding.monthlySalesText.text = "₹ 84,200" 
        binding.weeklySalesText.text = "₹ 12,500"
    }

    private fun setupChart() {
        val entries = ArrayList<BarEntry>()
        entries.add(BarEntry(0f, 4500f))
        entries.add(BarEntry(1f, 6200f))
        entries.add(BarEntry(2f, 3800f))
        entries.add(BarEntry(3f, 7100f))
        entries.add(BarEntry(4f, 5400f))
        entries.add(BarEntry(5f, 8900f))
        entries.add(BarEntry(6f, 6700f))

        val dataSet = BarDataSet(entries, "Daily Revenue (₹)")
        dataSet.color = Color.parseColor("#2E7D32") // Secondary Green
        dataSet.valueTextColor = Color.BLACK
        dataSet.setDrawValues(true)
        dataSet.valueTextSize = 10f
        
        val data = BarData(dataSet)
        binding.salesChart.data = data
        binding.salesChart.description.isEnabled = false
        
        // Fix X-Axis
        val days = arrayOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun")
        val xAxis = binding.salesChart.xAxis
        xAxis.valueFormatter = IndexAxisValueFormatter(days)
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.setDrawGridLines(false)
        xAxis.granularity = 1f
        xAxis.isGranularityEnabled = true

        // Fix Y-Axis
        val leftAxis = binding.salesChart.axisLeft
        leftAxis.axisMinimum = 0f
        leftAxis.setDrawGridLines(true)
        
        binding.salesChart.axisRight.isEnabled = false // Hide right axis

        binding.salesChart.animateY(1000)
        binding.salesChart.invalidate()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
