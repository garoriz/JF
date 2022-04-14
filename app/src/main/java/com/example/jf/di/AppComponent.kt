package com.example.jf.di

import com.example.jf.MainActivity
import com.example.jf.di.module.AppModule
import com.example.jf.di.module.NetModule
import com.example.jf.di.module.RepoModule
import com.example.jf.di.module.ViewModelModule
import com.example.jf.features.articleAboutCity.presentation.ArticleAboutCityFragment
import com.example.jf.features.cities.presentation.CitiesFragment
import dagger.Component

@Component(
    modules = [
        AppModule::class,
        NetModule::class,
        RepoModule::class,
        ViewModelModule::class
    ]
)
interface AppComponent {

    fun inject(mainActivity: MainActivity)

    fun inject(articleAboutCityFragment: ArticleAboutCityFragment)

    fun inject(citiesFragment: CitiesFragment)
}
