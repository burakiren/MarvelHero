package com.burakiren.data.apiService

import com.burakiren.domain.model.Comic
import com.burakiren.domain.model.MarvelResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("/v1/public/characters")
    fun getHeroes(): Single<MarvelResponse>

    @GET("/v1/public/characters/{id}/comics")
    fun getComics(@Path("id") id: Int): Single<MarvelResponse>

}