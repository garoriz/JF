package com.example.jf.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.lifecycle.lifecycleScope
import com.example.jf.R
import com.example.jf.data.CitiesRepositoryImpl
import com.example.jf.databinding.FragmentCitiesBinding
import com.example.jf.di.DIContainer
import com.example.jf.domain.usecase.GetCitiesUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CitiesFragment : Fragment(R.layout.fragment_cities) {
    private lateinit var binding: FragmentCitiesBinding
    private lateinit var getCitiesUseCase: GetCitiesUseCase

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentCitiesBinding.bind(view)
        initObjects()

        lifecycleScope.launch {

        }
    }

    private fun initObjects() {
        getCitiesUseCase = GetCitiesUseCase(
            citiesRepository = CitiesRepositoryImpl(
                api = DIContainer.api,
            ),
            dispatcher = Dispatchers.Default
        )
    }
}
