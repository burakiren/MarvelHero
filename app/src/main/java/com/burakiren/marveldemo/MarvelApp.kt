package com.burakiren.marveldemo

import android.app.Application
import com.burakiren.marveldemo.di.AppComponent
import com.burakiren.marveldemo.di.DaggerAppComponent

class MarvelApp : Application() {
    companion object {
        lateinit var component: AppComponent
            private set
    }

    override fun onCreate() {
        super.onCreate()

        component = DaggerAppComponent.builder()
            .application(this)
            .build()

    }
}