package com.example.jf.di.module

import android.content.Context
import com.example.jf.db.AppDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DbModule {
    @Singleton
    @Provides
    fun provideDb(context: Context): AppDatabase = AppDatabase(context)
}
