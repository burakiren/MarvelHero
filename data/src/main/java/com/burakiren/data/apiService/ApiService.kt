package com.burakiren.data.apiService

import com.burakiren.domain.model.Hero
import com.burakiren.domain.model.MarvelResponse
import io.reactivex.Single
import retrofit2.http.GET

interface ApiService {

    @GET("/v1/public/characters")
    fun getHeroes(): Single<MarvelResponse>

}