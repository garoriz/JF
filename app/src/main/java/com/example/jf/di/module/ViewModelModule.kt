package com.example.jf.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.jf.di.ViewModelKey
import com.example.jf.features.articleAboutCity.presentation.ArticleAboutCityViewModel
import com.example.jf.features.cities.presentation.CitiesViewModel
import com.example.jf.utils.AppViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {

    @Binds
    fun bindViewModelFactory(
        factory: AppViewModelFactory
    ): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(ArticleAboutCityViewModel::class)
    fun bindArticleAboutCityViewModel(
        viewModel: ArticleAboutCityViewModel
    ): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CitiesViewModel::class)
    fun bindCitiesViewModel(
        viewModel: CitiesViewModel
    ): ViewModel
}
