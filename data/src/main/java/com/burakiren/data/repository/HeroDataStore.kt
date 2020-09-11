package com.burakiren.data.repository

import com.burakiren.domain.model.ComicItem
import com.burakiren.domain.model.Hero
import io.reactivex.Single

interface HeroDataStore {
    fun getHeroes(): Single<List<Hero>>

    fun getComics(id: Int): Single<List<Hero>>
}