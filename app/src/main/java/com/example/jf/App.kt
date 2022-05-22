package com.example.jf

import android.app.Application
import com.example.jf.di.AppComponent
import com.example.jf.di.DaggerAppComponent
import com.example.jf.di.module.AppModule
import com.example.jf.di.module.NetModule
import com.instacart.library.truetime.TrueTime
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class App : Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.factory().create(this)
    }
}
