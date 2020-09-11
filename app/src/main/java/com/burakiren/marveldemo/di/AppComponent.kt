package com.burakiren.marveldemo.di

import android.app.Application
import com.burakiren.domain.repository.MarvelRepository
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder
        fun build(): AppComponent
    }

    fun exposeMarvelRepository(): MarvelRepository
}