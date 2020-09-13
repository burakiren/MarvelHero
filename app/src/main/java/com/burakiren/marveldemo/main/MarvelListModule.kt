package com.burakiren.marveldemo.main

import android.content.Context
import com.burakiren.domain.model.Hero
import com.burakiren.domain.repository.MarvelRepository
import com.burakiren.domain.usecase.GetComicsUseCase
import com.burakiren.domain.usecase.GetHeroesUseCase
import dagger.Module
import dagger.Provides
import java.util.*

@Module
class MarvelListModule {

    @Provides
    fun provideHeroesUseCase(marvelRepository: MarvelRepository) =
        GetHeroesUseCase(marvelRepository)

    @Provides
    fun provideComicUseCase(marvelRepository: MarvelRepository) =
        GetComicsUseCase(marvelRepository)

    @Provides
    fun provideComicsAdapter(context: Context) =
        ComicsAdapter(emptyList<Hero>().toMutableList() as ArrayList<Hero>?, context)

    @Provides
    fun providePresenter(
        getHeroesUseCase: GetHeroesUseCase,
        getComicsUseCase: GetComicsUseCase
    ) = MarvelListPresenter(getHeroesUseCase, getComicsUseCase)

}