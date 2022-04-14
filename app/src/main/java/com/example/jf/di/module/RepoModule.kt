package com.example.jf.di.module

import com.example.jf.features.articleAboutCity.data.CityRepositoryImpl
import com.example.jf.features.articleAboutCity.domain.repository.CityRepository
import com.example.jf.features.cities.data.CitiesRepositoryImpl
import com.example.jf.features.cities.domain.repository.CitiesRepository
import dagger.Binds
import dagger.Module

@Module
interface RepoModule {

    @Binds
    fun cityRepository(
        impl: CityRepositoryImpl
    ): CityRepository

    @Binds
    fun citiesRepository(
        impl: CitiesRepositoryImpl
    ): CitiesRepository
}
