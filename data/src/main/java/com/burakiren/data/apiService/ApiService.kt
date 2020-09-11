package com.burakiren.data.apiService

import com.burakiren.domain.model.MarvelResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("/v1/public/characters")
    fun getHeroes(@Query("offset") offset: Int): Single<MarvelResponse>

    @GET("/v1/public/characters/{id}/comics")
    fun getComics(@Path("id") id: Int, @Query("offset") offset: Int): Single<MarvelResponse>

}