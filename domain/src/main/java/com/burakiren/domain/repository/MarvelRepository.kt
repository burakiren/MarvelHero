package com.burakiren.domain.repository

import com.burakiren.domain.model.Hero
import io.reactivex.Completable
import io.reactivex.Single

interface MarvelRepository {

    fun insertOrUpdate(hero: Hero): Completable

    fun getAllHeroes(): Single<List<Hero>>

    fun getAllRemoteHeroes(offset: Int): Single<List<Hero>>

    fun getAllComics(id: Int, offset: Int): Single<List<Hero>>
}