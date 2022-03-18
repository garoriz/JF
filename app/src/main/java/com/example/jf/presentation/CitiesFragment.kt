package com.example.jf.presentation

import android.Manifest
import android.app.AlertDialog
import android.content.pm.PackageManager
import android.location.Location
import android.opengl.Visibility
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import com.example.jf.R
import com.example.jf.adapter.CityListAdapter
import com.example.jf.data.CitiesRepositoryImpl
import com.example.jf.data.api.response.cities.Data
import com.example.jf.databinding.FragmentCitiesBinding
import com.example.jf.di.DIContainer
import com.example.jf.domain.usecase.GetCitiesUseCase
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CitiesFragment : Fragment(R.layout.fragment_cities) {
    private lateinit var binding: FragmentCitiesBinding
    private lateinit var getCitiesUseCase: GetCitiesUseCase
    private var cityListAdapter: CityListAdapter? = null
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private val location = MutableLiveData<Location?>()

    private val locationPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        when {
            permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) -> {
                getLastLocation()
            }
            permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {
                getLastLocation()
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentCitiesBinding.bind(view)
        initObjects()
        requestLocationAccess()
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())

        location.observe(viewLifecycleOwner) {
            lifecycleScope.launch {
                val cities = getCitiesUseCase(getStringLocation(it)).data as MutableList

                cityListAdapter = CityListAdapter {
                    getInfoAboutCity()
                    cityListAdapter?.submitList(cities)
                }

                binding.cities.run {
                    adapter = cityListAdapter
                }

                cityListAdapter?.submitList(cities)
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
                        fusedLocationClient.lastLocation
                            .addOnSuccessListener {
                                location.value = it
                            }
                    } catch (exception: SecurityException) {
                        showMessage(R.string.error_getting_location)
                    }
                }
        }
    }

    private fun requestLocationAccess() {
        locationPermissionLauncher.launch(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        )
    }

    private fun getInfoAboutCity() {
        TODO("Not yet implemented")
    }

    private fun initObjects() {
        getCitiesUseCase = GetCitiesUseCase(
            citiesRepository = CitiesRepositoryImpl(
                api = DIContainer.api,
            ),
            dispatcher = Dispatchers.Default
        )
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
