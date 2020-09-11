package com.burakiren.data.repository

import com.burakiren.domain.model.ComicItem
import com.burakiren.domain.model.Hero
import io.reactivex.Single

interface HeroDataStore {
    fun getHeroes(offset: Int): Single<List<Hero>>

    fun getComics(id: Int, offset: Int): Single<List<Hero>>
}