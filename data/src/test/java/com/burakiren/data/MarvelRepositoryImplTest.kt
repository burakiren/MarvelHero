package com.burakiren.data

import com.burakiren.data.repository.HeroRemoteImpl
import com.burakiren.domain.model.Comic
import com.burakiren.domain.model.ComicItem
import com.burakiren.domain.model.Hero
import com.burakiren.domain.model.ThumbnailResponse
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Single
import org.junit.Test

class MarvelRepositoryImplTest {

    private val heroDao: MarvelDaoImpl = mock()

    private val heroRemote: HeroRemoteImpl = mock()

    private val heroMapper: HeroModelMapperImpl = mock()

    private val testSubject: HeroRepositoryImpl =
        HeroRepositoryImpl(heroRemote, heroDao, heroMapper)

    @Test
    fun getAllComics_emitsValues() {
        // GIVEN
        val entity = hero()
        val heroEntities = listOf(entity)
        whenever(heroRemote.getComics(123, 0)).thenReturn(Single.just(heroEntities))

        // WHEN
        val testObserver = testSubject.getAllComics(123, 0).test()

        // THEN
        testObserver.assertResult(listOf(hero()))
        testObserver.assertComplete()
    }

    @Test
    fun getAllHeros_emitsValues() {
        // GIVEN
        val entity = hero()
        val heroEntities = listOf(entity)
        whenever(heroRemote.getHeroes(0)).thenReturn(Single.just(heroEntities))

        // WHEN
        val testObserver = testSubject.getAllRemoteHeroes(0).test()

        // THEN
        testObserver.assertResult(listOf(hero()))
        testObserver.assertComplete()
    }

    private fun hero() = Hero(1, "test", "desc", ThumbnailResponse("path", "jpg"), comic())
    private fun comics() = MutableList(1) { ComicItem("", "") }
    private fun comic() = Comic(comics())


}