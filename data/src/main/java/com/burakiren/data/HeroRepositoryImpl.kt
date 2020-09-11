package com.burakiren.data

import com.burakiren.data.repository.HeroRemoteImpl
import com.burakiren.domain.model.Hero
import com.burakiren.domain.repository.MarvelRepository
import io.reactivex.Completable
import io.reactivex.Single

class HeroRepositoryImpl(
    private val heroRemoteImpl: HeroRemoteImpl,
    private val marvelDao: MarvelDaoImpl,
    private val mapper: HeroModelMapperImpl
) : MarvelRepository {

    override fun insertOrUpdate(hero: Hero): Completable =
        Completable.fromAction { marvelDao.insertOrUpdate(mapper.toEntity(hero)) }


    override fun getAllHeroes(): Single<List<Hero>> {
        return marvelDao.getAllHeroes()
            .map {
                it.map(mapper::fromEntity)
            }
    }

    override fun getAllRemoteHeroes(): Single<List<Hero>> {
        return heroRemoteImpl.getHeroes()
            .map {
                it
            }
    }


}