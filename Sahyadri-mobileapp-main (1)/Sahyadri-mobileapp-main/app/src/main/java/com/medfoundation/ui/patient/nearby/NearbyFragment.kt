package com.medfoundation.ui.patient.nearby

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.tabs.TabLayout
import com.medfoundation.R
import com.medfoundation.data.DummyData
import com.medfoundation.databinding.FragmentNearbyBinding

class NearbyFragment : Fragment(), OnMapReadyCallback {

    private var _binding: FragmentNearbyBinding? = null
    private val binding get() = _binding!!
    private var googleMap: GoogleMap? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNearbyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        setupRecyclerView()
        setupTabs()
    }

    private fun setupRecyclerView() {
        binding.nearbyRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.nearbyRecyclerView.adapter = NearbyAdapter(DummyData.dummyMedicals)
    }

    private fun setupTabs() {
        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (tab?.position == 0) {
                    binding.mapContainer.visibility = View.VISIBLE
                    binding.nearbyRecyclerView.visibility = View.GONE
                } else {
                    binding.mapContainer.visibility = View.GONE
                    binding.nearbyRecyclerView.visibility = View.VISIBLE
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
    }

    override fun onMapReady(map: GoogleMap) {
        googleMap = map
        val pune = LatLng(18.5204, 73.8567)
        
        for (medical in DummyData.dummyMedicals) {
            val location = LatLng(medical.latitude, medical.longitude)
            googleMap?.addMarker(MarkerOptions().position(location).title(medical.name))
        }
        
        googleMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(pune, 12f))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
