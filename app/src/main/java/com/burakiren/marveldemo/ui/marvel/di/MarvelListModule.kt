package com.burakiren.marveldemo.ui.marvel.di

import android.content.Context
import com.burakiren.domain.model.Hero
import com.burakiren.domain.repository.MarvelRepository
import com.burakiren.domain.usecase.GetComicsUseCase
import com.burakiren.domain.usecase.GetHeroesUseCase
import com.burakiren.marveldemo.di.ContextModule
import com.burakiren.marveldemo.ui.marvel.ComicsAdapter
import com.burakiren.marveldemo.ui.marvel.MarvelListPresenter
import dagger.Module
import dagger.Provides
import java.util.*

@Module(includes = [ContextModule::class])
class MarvelListModule {

    @Provides
    fun provideHeroesUseCase(marvelRepository: MarvelRepository) =
        GetHeroesUseCase(marvelRepository)

    @Provides
    fun provideComicUseCase(marvelRepository: MarvelRepository) =
        GetComicsUseCase(marvelRepository)

    @Provides
    fun provideComicsAdapter(context: Context) =
        ComicsAdapter(
            emptyList<Hero>().toMutableList() as ArrayList<Hero>?,
            context
        )

    @Provides
    fun providePresenter(
        getHeroesUseCase: GetHeroesUseCase,
        getComicsUseCase: GetComicsUseCase
    ) = MarvelListPresenter(
        getHeroesUseCase,
        getComicsUseCase
    )

}