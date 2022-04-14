package com.example.jf.di.module

import com.example.jf.features.articleAboutCity.data.api.mappers.CityMapper
import com.example.jf.features.cities.data.api.mappers.CitiesMapper
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

@Module
class AppModule {

    @Provides
    fun provideCityMapper(): CityMapper = CityMapper()

    @Provides
    fun provideCitiesMapper(): CitiesMapper = CitiesMapper()

    @Provides
    fun provideDefaultDispatcher(): CoroutineDispatcher = Dispatchers.Default
}
