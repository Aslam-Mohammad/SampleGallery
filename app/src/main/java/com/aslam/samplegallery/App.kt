package com.aslam.samplegallery

import android.app.Application
import com.aslam.samplegallery.di.sharedPrefModule
import com.aslam.samplegallery.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin


class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(viewModelModule+sharedPrefModule
            )
        }

    }
}