package com.medfoundation.ui.admin.reports

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.utils.ColorTemplate
import com.medfoundation.databinding.FragmentAdminReportsBinding

class AdminReportsFragment : Fragment() {

    private var _binding: FragmentAdminReportsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAdminReportsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupPieChart()
        setupDailyBarChart()

        binding.exportBtn.setOnClickListener {
            Toast.makeText(requireContext(), "Export feature coming soon!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupPieChart() {
        val entries = ArrayList<PieEntry>()
        entries.add(PieEntry(40f, "MedPlus"))
        entries.add(PieEntry(30f, "Apollo"))
        entries.add(PieEntry(15f, "Jan Aushadhi"))
        entries.add(PieEntry(10f, "Sahyadri"))
        entries.add(PieEntry(5f, "LifeCare"))

        val dataSet = PieDataSet(entries, "Pharmacies")
        dataSet.colors = ColorTemplate.MATERIAL_COLORS.toList()
        val data = PieData(dataSet)
        binding.pieChart.data = data
        binding.pieChart.description.isEnabled = false
        binding.pieChart.animateY(1000)
        binding.pieChart.invalidate()
    }

    private fun setupDailyBarChart() {
        val entries = ArrayList<BarEntry>()
        for (i in 0 until 30) {
            entries.add(BarEntry(i.toFloat(), (10..50).random().toFloat()))
        }

        val dataSet = BarDataSet(entries, "Daily Transactions")
        dataSet.color = Color.parseColor("#2E7D32")
        val data = BarData(dataSet)
        binding.dailyBarChart.data = data
        binding.dailyBarChart.description.isEnabled = false
        binding.dailyBarChart.animateY(1000)
        binding.dailyBarChart.invalidate()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
