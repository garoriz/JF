package com.example.jf.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.jf.di.ViewModelKey
import com.example.jf.features.articleAboutCity.presentation.ArticleAboutCityViewModel
import com.example.jf.features.cities.presentation.CitiesViewModel
import com.example.jf.features.editProfile.presentation.EditProfileViewModel
import com.example.jf.features.login.presentation.LoginViewModel
import com.example.jf.features.main.presentation.MainViewModel
import com.example.jf.features.myProfile.presentation.MyProfileViewModel
import com.example.jf.features.newPost.presentation.NewPostViewModel
import com.example.jf.features.notes.presentation.NotesViewModel
import com.example.jf.features.other.presentation.OtherViewModel
import com.example.jf.features.otherProfile.presentation.OtherProfileViewModel
import com.example.jf.features.post.presentation.PostViewModel
import com.example.jf.features.registration.presentation.RegistrationViewModel
import com.example.jf.features.settings.presentation.SettingsViewModel
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

    @Binds
    @IntoMap
    @ViewModelKey(NotesViewModel::class)
    fun bindNotesViewModel(
        viewModel: NotesViewModel
    ): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel::class)
    fun bindLoginViewModel(
        viewModel: LoginViewModel
    ): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(EditProfileViewModel::class)
    fun bindEditProfileViewModel(
        viewModel: EditProfileViewModel
    ): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    fun bindMainViewModel(
        viewModel: MainViewModel
    ): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MyProfileViewModel::class)
    fun bindMyProfileViewModel(
        viewModel: MyProfileViewModel
    ): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(NewPostViewModel::class)
    fun bindNewPostViewModel(
        viewModel: NewPostViewModel
    ): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(OtherViewModel::class)
    fun bindOtherViewModel(
        viewModel: OtherViewModel
    ): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(OtherProfileViewModel::class)
    fun bindOtherProfileViewModel(
        viewModel: OtherProfileViewModel
    ): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(PostViewModel::class)
    fun bindPostViewModel(
        viewModel: PostViewModel
    ): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(RegistrationViewModel::class)
    fun bindRegistrationViewModel(
        viewModel: RegistrationViewModel
    ): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SettingsViewModel::class)
    fun bindSettingsViewModel(
        viewModel: SettingsViewModel
    ): ViewModel
}
