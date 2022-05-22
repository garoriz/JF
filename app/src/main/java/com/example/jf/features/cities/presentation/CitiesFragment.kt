package com.example.jf.features.cities.presentation

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.navigation.findNavController
import com.example.jf.MainActivity
import com.example.jf.R
import com.example.jf.databinding.FragmentCitiesBinding
import com.example.jf.features.cities.domain.usecase.GetCitiesUseCase
import com.example.jf.features.cities.presentation.adapter.CityListAdapter
import com.example.jf.utils.AppViewModelFactory
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.snackbar.Snackbar
import javax.inject.Inject

private const val ARG_NAME = "name"

class CitiesFragment : Fragment(R.layout.fragment_cities) {

    @Inject
    lateinit var factory: AppViewModelFactory
    private lateinit var binding: FragmentCitiesBinding
    private var cityListAdapter: CityListAdapter? = null
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private val location = MutableLiveData<Location?>()
    private val viewModel: CitiesViewModel by viewModels {
        factory
    }

    private val requestMultiplePermissions = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { getLastLocation() }

    override fun onCreate(savedInstanceState: Bundle?) {
        (activity as MainActivity).appComponent.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentCitiesBinding.bind(view)
        initObservers()
        requestLocationAccess()

        updateCities()

        with(binding) {
            swipeContainer.setOnRefreshListener {
                updateCities()
                swipeContainer.isRefreshing = false
            }
            btnUpdate.setOnClickListener {
                updateCities()
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
            viewModel.onGetCities(getStringLocation(it))
        }
    }

    private fun getInfoAboutCity(name: String) {
        view?.findNavController()
            ?.navigate(
                R.id.action_navigation_cities_to_articleFragment,
                bundleOf(ARG_NAME to name)
            )
    }

    private fun initObservers() {
        viewModel.cities.observe(viewLifecycleOwner) { it ->
            it.fold(onSuccess = {

                with (binding) {
                    tvLoading.visibility = View.GONE
                    btnUpdate.visibility = View.GONE
                }

                cityListAdapter = CityListAdapter { clickedCity ->
                    getInfoAboutCity(clickedCity)
                    cityListAdapter?.submitList(it)
                }

                binding.cities.run {
                    adapter = cityListAdapter
                }

                cityListAdapter?.submitList(it)
            }, onFailure = {
                Log.e("e", it.message.toString())
            })
            viewModel.error.removeObservers(viewLifecycleOwner)
        }

        viewModel.error.observe(viewLifecycleOwner) {
            when (it) {
                is Exception -> {
                    showMessage(R.string.error)
                }
            }
        }
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
