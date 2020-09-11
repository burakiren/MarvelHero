package com.burakiren.data.repository

import com.burakiren.data.apiService.ApiService
import com.burakiren.domain.model.Hero
import io.reactivex.Single

class HeroRemoteImpl constructor(private val api: ApiService) : HeroDataStore {

    override fun getHeroes(offset: Int): Single<List<Hero>> {

        return api.getHeroes(offset).map { it.data.results }
    }

    override fun getComics(id: Int, offset: Int): Single<List<Hero>> {

        return api.getComics(id, offset).map { it.data.results }
    }

}