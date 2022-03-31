package com.example.jf.features.article.presentation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import coil.load
import com.example.jf.R
import com.example.jf.databinding.FragmentArticleBinding
import com.example.jf.features.article.data.CityRepositoryImpl
import com.example.jf.features.article.data.mappers.CityMapper
import com.example.jf.features.article.di.DIContainer
import com.example.jf.features.article.domain.GetCityUseCase
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

private const val ARG_NAME = "name"

class ArticleFragment : Fragment(R.layout.fragment_article) {

    private lateinit var binding: FragmentArticleBinding
    private lateinit var getCityUseCase: GetCityUseCase

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentArticleBinding.bind(view)
        initObjects()

        val name = arguments?.getString(ARG_NAME).toString()
        lifecycleScope.launch {
            try {
                val city = getCityUseCase(name)
                with(binding) {
                    tvHeader.text = city.title
                    ivCity.load(city.sourcePhoto)
                    tvContent.text = city.description
                    binding.tvSource.visibility = View.VISIBLE
                }
            } catch (ex: Exception) {
                showMessage(R.string.no_internet)
            }
        }
    }

    private fun initObjects() {
        getCityUseCase = GetCityUseCase(
            cityRepository = CityRepositoryImpl(
                api = DIContainer.api,
                cityMapper = CityMapper()
            ),
            dispatcher = Dispatchers.Default
        )
    }

    private fun showMessage(stringId: Int) {
        Snackbar.make(
            binding.root,
            stringId,
            Snackbar.LENGTH_LONG
        ).show()
    }
}
