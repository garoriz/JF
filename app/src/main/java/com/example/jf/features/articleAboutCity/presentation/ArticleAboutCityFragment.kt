package com.example.jf.features.articleAboutCity.presentation

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import coil.load
import com.example.jf.MainActivity
import com.example.jf.R
import com.example.jf.databinding.FragmentArticleAboutCityBinding
import com.example.jf.utils.AppViewModelFactory
import com.google.android.material.snackbar.Snackbar
import javax.inject.Inject

private const val ARG_NAME = "name"

class ArticleAboutCityFragment : Fragment(R.layout.fragment_article_about_city) {

    @Inject
    lateinit var factory: AppViewModelFactory
    private lateinit var binding: FragmentArticleAboutCityBinding
    private val viewModel: ArticleAboutCityViewModel by viewModels {
        factory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        (activity as MainActivity).appComponent.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentArticleAboutCityBinding.bind(view)
        initObservers()

        val name = arguments?.getString(ARG_NAME).toString()
        viewModel.onGetInfoAboutCity(name)
    }

    private fun initObservers() {
        viewModel.city.observe(viewLifecycleOwner) { it ->
            it.fold(onSuccess = {
                with(binding) {
                    tvLoading.visibility = View.GONE
                    tvHeader.text = it.title
                    ivCity.load(it.sourcePhoto)
                    tvContent.text = it.description
                    binding.tvSource.visibility = View.VISIBLE
                }
            }, onFailure = {
                Log.e("e", it.message.toString())
            })
        }

        viewModel.error.observe(viewLifecycleOwner) {
            when (it) {
                is Exception -> {
                    showMessage(R.string.no_internet)
                }
            }
        }
    }

    private fun showMessage(stringId: Int) {
        Snackbar.make(
            binding.root,
            stringId,
            Snackbar.LENGTH_LONG
        ).show()
    }
}
