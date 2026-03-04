package com.medfoundation.ui.admin.dashboard

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.medfoundation.data.DummyData
import com.medfoundation.databinding.FragmentAdminDashboardBinding

class AdminDashboardFragment : Fragment() {

    private var _binding: FragmentAdminDashboardBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAdminDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupStats()
        setupChart()
        setupRecentActivity()
    }

    private fun setupStats() {
        val stats = DummyData.dummyAdminStats
        binding.statFamilies.text = stats.totalFamilies.toString()
        binding.statPatients.text = stats.totalPatients.toString()
        binding.statMedicals.text = stats.activeMedicals.toString()
        binding.statBills.text = stats.totalTransactionsToday.toString()
        binding.statDiscounts.text = "₹${(stats.totalDiscountGiven / 100000).format(2)}L"
        binding.statPending.text = stats.pendingApprovals.toString()
    }

    private fun setupChart() {
        val entries = ArrayList<BarEntry>()
        entries.add(BarEntry(0f, 120f))
        entries.add(BarEntry(1f, 150f))
        entries.add(BarEntry(2f, 180f))
        entries.add(BarEntry(3f, 220f))
        entries.add(BarEntry(4f, 250f))
        entries.add(BarEntry(5f, 300f))

        val dataSet = BarDataSet(entries, "Transactions")
        dataSet.color = Color.parseColor("#1565C0")
        val data = BarData(dataSet)
        binding.performanceChart.data = data
        binding.performanceChart.description.isEnabled = false
        binding.performanceChart.animateY(1000)
        binding.performanceChart.invalidate()
    }

    private fun setupRecentActivity() {
        val activities = listOf(
            "New Family Registered: Sharma Family",
            "MedPlus Pharmacy generated bill: BILL-1293",
            "Apollo Pharmacy registered as partner",
            "Card Renewed: MEDF-2024-0012",
            "Admin updated discount settings to 25%"
        )
        binding.recentActivityContainer.removeAllViews()
        for (activity in activities) {
            val view = LayoutInflater.from(requireContext()).inflate(android.R.layout.simple_list_item_1, binding.recentActivityContainer, false) as TextView
            view.text = activity
            view.textSize = 14f
            binding.recentActivityContainer.addView(view)
        }
    }

    private fun Double.format(digits: Int) = "%.${digits}f".format(this)

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
