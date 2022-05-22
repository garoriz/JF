package com.example.jf.di.module

import com.example.jf.features.articleAboutCity.data.api.mappers.CityMapper
import com.example.jf.features.cities.data.api.mappers.CitiesMapper
import com.example.jf.features.editProfile.data.mappers.UserMapperInEditProfile
import com.example.jf.features.main.data.mappers.PostMapper
import com.example.jf.features.main.data.mappers.UserMapperInMainFragment
import com.example.jf.features.myProfile.data.mappers.PostMapperInMyProfile
import com.example.jf.features.myProfile.data.mappers.UserMapper
import com.example.jf.features.newPost.data.mappers.UserMapperInNewPost
import com.example.jf.features.other.data.mappers.UserMapperInOther
import com.example.jf.features.otherProfile.data.mappers.UserPostMapper
import com.example.jf.features.post.data.mappers.UserMapperInPost
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

    @Provides
    fun provideUserMapperInEditProfile(): UserMapperInEditProfile = UserMapperInEditProfile()

    @Provides
    fun providePostMapper(): PostMapper = PostMapper()

    @Provides
    fun provideUserMapperInMainFragment(): UserMapperInMainFragment = UserMapperInMainFragment()

    @Provides
    fun provideUserMapper(): UserMapper = UserMapper()

    @Provides
    fun providePostMapperInMyProfile(): PostMapperInMyProfile = PostMapperInMyProfile()

    @Provides
    fun provideUserMapperInNewPost(): UserMapperInNewPost = UserMapperInNewPost()

    @Provides
    fun provideUserMapperInOther(): UserMapperInOther = UserMapperInOther()

    @Provides
    fun provideUserPostMapper(): UserPostMapper = UserPostMapper()

    @Provides
    fun provideUserMapperInPost(): UserMapperInPost = UserMapperInPost()
}
