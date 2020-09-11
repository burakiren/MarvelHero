package com.burakiren.marveldemo.main

import com.burakiren.domain.repository.MarvelRepository
import com.burakiren.domain.usecase.GetComicsUseCase
import com.burakiren.domain.usecase.GetHeroesUseCase
import dagger.Module
import dagger.Provides

@Module
class MarvelListModule {

    @Provides
    fun provideHeroesUseCase(marvelRepository: MarvelRepository) =
        GetHeroesUseCase(marvelRepository)

    @Provides
    fun provideComicUseCase(marvelRepository: MarvelRepository) =
        GetComicsUseCase(marvelRepository)

    @Provides
    fun providePresenter(
        getHeroesUseCase: GetHeroesUseCase,
        getComicsUseCase: GetComicsUseCase
    ) = MarvelListPresenter(getHeroesUseCase, getComicsUseCase)

}