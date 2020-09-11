package com.burakiren.domain.usecase

import com.burakiren.domain.model.Hero
import com.burakiren.domain.repository.MarvelRepository
import io.reactivex.Single

class GetComicsUseCase(private val repository: MarvelRepository) {
    fun getAllComics(id: Int, offset: Int): Single<List<Hero>> = repository.getAllComics(id, offset)
}