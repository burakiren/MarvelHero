package com.burakiren.marveldemo.main

import com.burakiren.marveldemo.di.AppComponent
import com.burakiren.marveldemo.di.PerScreen
import dagger.Component

@PerScreen
@Component(modules = [MarvelListModule::class],
        dependencies = [AppComponent::class]
)
interface MarvelListComponent {
    fun inject(view: MarvelListView)
}