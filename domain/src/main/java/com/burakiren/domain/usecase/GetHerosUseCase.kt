package com.burakiren.domain.usecase

import com.burakiren.domain.model.Hero
import com.burakiren.domain.repository.MarvelRepository
import io.reactivex.Single

class GetHeroesUseCase(private val repository: MarvelRepository) {
    fun getAllHeroes(): Single<List<Hero>> = repository.getAllRemoteHeroes()
}