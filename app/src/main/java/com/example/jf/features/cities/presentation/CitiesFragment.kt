package com.example.jf.features.cities.presentation

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.example.jf.R
import com.example.jf.databinding.FragmentCitiesBinding
import com.example.jf.features.cities.presentation.adapter.CityListAdapter
import com.example.jf.features.cities.data.CitiesRepositoryImpl
import com.example.jf.features.cities.data.mappers.CitiesMapper
import com.example.jf.features.cities.di.DIContainer
import com.example.jf.features.cities.domain.GetCitiesUseCase
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

private const val ARG_NAME = "name"

class CitiesFragment : Fragment(R.layout.fragment_cities) {
    private lateinit var binding: FragmentCitiesBinding
    private lateinit var getCitiesUseCase: GetCitiesUseCase
    private var cityListAdapter: CityListAdapter? = null
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private val location = MutableLiveData<Location?>()

    private val requestMultiplePermissions = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { getLastLocation() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentCitiesBinding.bind(view)
        initObjects()
        requestLocationAccess()

        updateCities()

        with(binding) {
            swipeContainer.setOnRefreshListener {
                updateCities()
                swipeContainer.isRefreshing = false
            }
        }
    }

    private fun getStringLocation(location: Location?): String {
        val latitudeString = location?.latitude.toString()
        val longitudeString = location?.longitude.toString()
        if (longitudeString[0] == '-') {
            return latitudeString + longitudeString
        }
        return "$latitudeString+$longitudeString"
    }

    private fun getLastLocation() {
        if (checkPermission(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
        ) {
            fusedLocationClient.lastLocation
                .addOnSuccessListener {
                    try {
                        location.value = it
                    } catch (exception: SecurityException) {
                        showMessage(R.string.error_getting_location)
                    }
                }
        }
    }

    private fun requestLocationAccess() {
        requestMultiplePermissions.launch(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        )
    }

    private fun updateCities() {
        location.observe(viewLifecycleOwner) {
            lifecycleScope.launch {
                try {
                    val cities = getCitiesUseCase(getStringLocation(it))

                    cityListAdapter = CityListAdapter {
                        getInfoAboutCity(it)
                        cityListAdapter?.submitList(cities)
                    }

                    binding.cities.run {
                        adapter = cityListAdapter
                    }

                    cityListAdapter?.submitList(cities)
                } catch (ex: Exception) {
                    showMessage(R.string.no_internet)
                }
            }
        }
    }

    private fun getInfoAboutCity(name: String) {
        view?.findNavController()
            ?.navigate(
                R.id.action_navigation_cities_to_articleFragment,
                bundleOf(ARG_NAME to name)
            )
    }

    private fun initObjects() {
        getCitiesUseCase = GetCitiesUseCase(
            citiesRepository = CitiesRepositoryImpl(
                api = DIContainer.api,
                citiesMapper = CitiesMapper()
            ),
            dispatcher = Dispatchers.Default
        )
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
    }

    private fun checkPermission(vararg perm: String): Boolean {
        val havePermissions = perm.toList().all {
            ContextCompat.checkSelfPermission(requireContext(), it) ==
                    PackageManager.PERMISSION_GRANTED
        }
        if (!havePermissions) {
            return false
        }
        return true
    }

    private fun showMessage(stringId: Int) {
        Snackbar.make(
            binding.root,
            stringId,
            Snackbar.LENGTH_LONG
        ).show()
    }
}