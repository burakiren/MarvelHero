package com.burakiren.data.repository

import com.burakiren.data.apiService.ApiService
import com.burakiren.domain.model.Hero
import io.reactivex.Single

class HeroRemoteImpl constructor(private val api: ApiService) : HeroDataStore {

    override fun getHeroes(): Single<List<Hero>> {

        return api.getHeroes().map { it.data.results }
    }

    override fun getComics(id: Int): Single<List<Hero>> {

        return api.getComics(id).map { it.data.results }
    }

}