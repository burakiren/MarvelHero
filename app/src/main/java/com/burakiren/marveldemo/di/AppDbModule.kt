package com.burakiren.marveldemo.di

import android.content.Context
import com.burakiren.data.HeroModelMapperImpl
import com.burakiren.data.HeroRepositoryImpl
import com.burakiren.data.MarvelDaoImpl
import com.burakiren.data.apiService.ApiService
import com.burakiren.data.createHeroDao
import com.burakiren.data.repository.HeroRemoteImpl
import com.burakiren.domain.repository.MarvelRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppDbModule {

    @Singleton
    @Provides
    fun provideHeroDao(context: Context) = createHeroDao(context)

    @Singleton
    @Provides
    fun provideHeroModelMapper() = HeroModelMapperImpl()

    @Singleton
    @Provides
    fun provideHeroRepository(
        heroRemoteImpl: HeroRemoteImpl,
        heroDao: MarvelDaoImpl,
        mapper: HeroModelMapperImpl
    ): MarvelRepository = HeroRepositoryImpl(heroRemoteImpl, heroDao, mapper)

    @Singleton
    @Provides
    fun provideHeroRemoteImpl(apiService: ApiService) = HeroRemoteImpl(apiService)
}