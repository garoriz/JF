package com.example.jf.di

import android.content.Context
import com.example.jf.MainActivity
import com.example.jf.di.module.*
import com.example.jf.features.articleAboutCity.presentation.ArticleAboutCityFragment
import com.example.jf.features.cities.presentation.CitiesFragment
import com.example.jf.features.editProfile.presentation.EditProfileFragment
import com.example.jf.features.login.presentation.LoginFragment
import com.example.jf.features.main.presentation.MainFragment
import com.example.jf.features.myProfile.presentation.MyProfileFragment
import com.example.jf.features.newPost.presentation.NewPostFragment
import com.example.jf.features.notes.presentation.NotesFragment
import com.example.jf.features.other.presentation.OtherFragment
import com.example.jf.features.otherProfile.presentation.OtherProfileFragment
import com.example.jf.features.post.presentation.PostFragment
import com.example.jf.features.registration.presentation.RegistrationFragment
import com.example.jf.features.settings.presentation.SettingsFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
        NetModule::class,
        RepoModule::class,
        ViewModelModule::class,
        DbModule::class,
        FirebaseModule::class
    ]
)
interface AppComponent {

    fun inject(mainActivity: MainActivity)

    fun inject(articleAboutCityFragment: ArticleAboutCityFragment)

    fun inject(citiesFragment: CitiesFragment)

    fun inject(notesFragment: NotesFragment)

    fun inject(loginFragment: LoginFragment)

    fun inject(editProfileFragment: EditProfileFragment)

    fun inject(mainFragment: MainFragment)

    fun inject(myProfileFragment: MyProfileFragment)

    fun inject(newPostFragment: NewPostFragment)

    fun inject(otherFragment: OtherFragment)

    fun inject(otherProfileFragment: OtherProfileFragment)

    fun inject(postFragment: PostFragment)

    fun inject(registrationFragment: RegistrationFragment)

    fun inject(settingsFragment: SettingsFragment)

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }
}
