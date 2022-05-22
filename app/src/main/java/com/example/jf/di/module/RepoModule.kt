package com.example.jf.di.module

import com.example.jf.features.articleAboutCity.data.CityRepositoryImpl
import com.example.jf.features.articleAboutCity.domain.repository.CityRepository
import com.example.jf.features.cities.data.CitiesRepositoryImpl
import com.example.jf.features.cities.domain.repository.CitiesRepository
import com.example.jf.features.editProfile.data.UserRepoInEditProfileImpl
import com.example.jf.features.editProfile.domain.repositories.UserRepoInEditProfile
import com.example.jf.features.login.data.FirebaseAuthRepoImpl
import com.example.jf.features.login.domain.repo.FirebaseAuthRepo
import com.example.jf.features.main.data.repoImpl.PostListRepoImpl
import com.example.jf.features.main.data.repoImpl.UserRepoInMainImpl
import com.example.jf.features.main.domain.repo.PostListRepo
import com.example.jf.features.main.domain.repo.UserRepoInMain
import com.example.jf.features.myProfile.data.repoImpl.PostRepoInMyProfileImpl
import com.example.jf.features.myProfile.data.repoImpl.UserRepoImpl
import com.example.jf.features.myProfile.domain.repo.PostRepoInMyProfile
import com.example.jf.features.myProfile.domain.repo.UserRepo
import com.example.jf.features.newPost.data.repoImpl.NewPostRepoImpl
import com.example.jf.features.newPost.data.repoImpl.UserRepoInNewPostImpl
import com.example.jf.features.newPost.domain.repo.NewPostRepo
import com.example.jf.features.newPost.domain.repo.UserRepoInNewPost
import com.example.jf.features.other.data.repoImpl.UserRepoInOtherImpl
import com.example.jf.features.other.domain.repo.UserRepoInOther
import com.example.jf.features.otherProfile.data.repoImpl.OtherUserRepoImpl
import com.example.jf.features.otherProfile.data.repoImpl.UserPostRepoImpl
import com.example.jf.features.otherProfile.domain.repo.OtherUserRepo
import com.example.jf.features.otherProfile.domain.repo.UserPostRepo
import com.example.jf.features.post.data.repoImpl.PostRepoImpl
import com.example.jf.features.post.data.repoImpl.UserRepoInPostImpl
import com.example.jf.features.post.domain.repo.PostRepo
import com.example.jf.features.post.domain.repo.UserRepoInPost
import com.example.jf.features.registration.data.repoImpl.RegistrationUserRepoImpl
import com.example.jf.features.registration.domain.repo.RegistrationUserRepo
import com.example.jf.features.settings.data.repoImpl.UserRepoInSettingsImpl
import com.example.jf.features.settings.domain.repo.UserRepoInSettings
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

    @Binds
    fun firebaseAuthRepo(
        impl: FirebaseAuthRepoImpl
    ): FirebaseAuthRepo

    @Binds
    fun userRepoInEditProfile(
        impl: UserRepoInEditProfileImpl
    ): UserRepoInEditProfile

    @Binds
    fun postListRepo(
        impl: PostListRepoImpl
    ): PostListRepo

    @Binds
    fun userRepoInMain(
        impl: UserRepoInMainImpl
    ): UserRepoInMain

    @Binds
    fun postRepoInMyProfile(
        impl: PostRepoInMyProfileImpl
    ): PostRepoInMyProfile

    @Binds
    fun userRepo(
        impl: UserRepoImpl
    ): UserRepo

    @Binds
    fun newPostRepo(
        impl: NewPostRepoImpl
    ): NewPostRepo

    @Binds
    fun userRepoInNewPost(
        impl: UserRepoInNewPostImpl
    ): UserRepoInNewPost

    @Binds
    fun userRepoInOther(
        impl: UserRepoInOtherImpl
    ): UserRepoInOther

    @Binds
    fun userPostRepo(
        impl: UserPostRepoImpl
    ): UserPostRepo

    @Binds
    fun otherUserRepo(
        impl: OtherUserRepoImpl
    ): OtherUserRepo

    @Binds
    fun postRepo(
        impl: PostRepoImpl
    ): PostRepo

    @Binds
    fun userRepoInPost(
        impl: UserRepoInPostImpl
    ): UserRepoInPost

    @Binds
    fun registrationUserRepo(
        impl: RegistrationUserRepoImpl
    ): RegistrationUserRepo

    @Binds
    fun userRepoInSettings(
        impl: UserRepoInSettingsImpl
    ): UserRepoInSettings
}
