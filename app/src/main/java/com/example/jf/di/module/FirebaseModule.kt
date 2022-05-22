package com.example.jf.di.module

import com.example.jf.firebase.DbRefService
import com.example.jf.firebase.FirebaseAuthService
import com.example.jf.firebase.StorageRefService
import dagger.Module
import dagger.Provides

@Module
class FirebaseModule {
    @Provides
    fun provideFirebaseAuthService(): FirebaseAuthService = FirebaseAuthService.getInstance()

    @Provides
    fun provideDbRefService(): DbRefService = DbRefService.getInstance()

    @Provides
    fun provideStorageRefService(): StorageRefService = StorageRefService.getInstance()
}
