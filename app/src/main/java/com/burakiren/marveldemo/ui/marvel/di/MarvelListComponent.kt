package com.burakiren.marveldemo.ui.marvel.di

import com.burakiren.marveldemo.di.AppComponent
import com.burakiren.marveldemo.di.PerScreen
import com.burakiren.marveldemo.ui.marvel.MarvelListView
import dagger.Component

@PerScreen
@Component(modules = [MarvelListModule::class],
        dependencies = [AppComponent::class]
)
interface MarvelListComponent {
    fun inject(view: MarvelListView)
}