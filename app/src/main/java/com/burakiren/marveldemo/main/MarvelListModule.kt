package com.burakiren.marveldemo.main

import com.burakiren.domain.repository.MarvelRepository
import com.burakiren.domain.usecase.GetHeroesUseCase
import dagger.Module
import dagger.Provides

@Module
class MarvelListModule {

    @Provides
    fun provideGerHeroesUseCase(marvelRepository: MarvelRepository) =
        GetHeroesUseCase(marvelRepository)

    @Provides
    fun providePresenter(getHeroesUseCase: GetHeroesUseCase) = MarvelListPresenter(getHeroesUseCase)
}