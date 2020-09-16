package com.burakiren.marveldemo

import com.burakiren.domain.model.Comic
import com.burakiren.domain.model.ComicItem
import com.burakiren.domain.model.Hero
import com.burakiren.domain.model.ThumbnailResponse
import com.burakiren.domain.repository.MarvelRepository
import com.burakiren.domain.usecase.GetComicsUseCase
import com.burakiren.domain.usecase.GetHeroesUseCase
import com.burakiren.marveldemo.ui.marvel.MarvelListPresenter
import com.burakiren.marveldemo.ui.marvel.MarvelListView
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Single
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import io.reactivex.schedulers.TestScheduler
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*

class MarvelPresenterTest {

    private lateinit var testSubject: MarvelListPresenter

    private val view: MarvelListView = mock()

    private val repository: MarvelRepository = mock()

    private val getComicsUseCase: GetComicsUseCase = GetComicsUseCase(repository)

    private val getHeroesUseCase: GetHeroesUseCase = GetHeroesUseCase(repository)

    private val testScheduler = TestScheduler()

    private fun hero() = Hero(1, "test", "desc", ThumbnailResponse("path", "jpg"), comic())
    private fun comics() = MutableList(1) { ComicItem("", "") }
    private fun comic() = Comic(comics())

    @Before
    fun setUp() {
        RxJavaPlugins.setIoSchedulerHandler { _ -> testScheduler }
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { _ -> Schedulers.trampoline() }

        testSubject = MarvelListPresenter(getHeroesUseCase, getComicsUseCase)
        testSubject.start(view)
    }

    @Test
    fun loadHeroes_givenLoadHeroes_callsViewOnonLoadHeroesSuccess() {

        val heroEntities = listOf(hero())

        // GIVEN
        whenever(repository.getAllRemoteHeroes(0)).thenReturn(Single.just(heroEntities))

        // WHEN
        testSubject.loadHeroes(0)
        testScheduler.triggerActions()

        // THEN
        verify(view).onLoadHeroesSuccess(heroEntities)
    }

    @Test
    fun loadComics_givenLoadComicsSuccess_callsViewOnLoadComicsSuccess() {

        val heroEntities = listOf(hero())

        // GIVEN
        whenever(repository.getAllComics(123, 0)).thenReturn(Single.just(heroEntities))

        // WHEN
        testSubject.fetchComics(123, 0)
        testScheduler.triggerActions()

        // THEN
        verify(view).onLoadComicsSuccess(heroEntities)
    }

    @Test
    fun loadComics_givenLoadcomicsError_andViewNotAttached_doesNotCallViewonLoadComicsError() {
        // GIVEN
        val throwable = RuntimeException()
        whenever(repository.getAllComics(123, 0)).thenReturn(Single.error(throwable))
        testSubject.stop()

        // WHEN
        testSubject.fetchComics(123, 0)
        testScheduler.triggerActions()

        // THEN
        verify(view, never()).onLoadHeroesError(throwable)
    }

    @Test
    fun loadHeroes_givenLoadHeroesError_callsViewOnLoadHeroesError() {
        // GIVEN
        val throwable = RuntimeException()
        whenever(repository.getAllRemoteHeroes(0)).thenReturn(Single.error(throwable))

        // WHEN
        testSubject.loadHeroes(0)
        testScheduler.triggerActions()

        // THEN
        verify(view).onLoadHeroesError(throwable)
    }

    @Test
    fun loadHeroes_givenLoadHeroesError_andViewNotAttached_doesNotCallViewonLoadHeroesError() {
        // GIVEN
        val throwable = RuntimeException()
        whenever(repository.getAllRemoteHeroes(0)).thenReturn(Single.error(throwable))
        testSubject.stop()

        // WHEN
        testSubject.loadHeroes(0)
        testScheduler.triggerActions()

        // THEN
        verify(view, never()).onLoadHeroesError(throwable)
    }

    @Test
    fun loadHeroes_callsViewShowLoading_andThenHideLoading() {

        val heroEntities = listOf(hero())

        // GIVEN
        whenever(repository.getAllRemoteHeroes(0)).thenReturn(Single.just(heroEntities))

        // WHEN
        testSubject.loadHeroes(0)
        testScheduler.triggerActions()

        // THEN
        val inOrder = inOrder(view, view)
        inOrder.verify(view).showLoading()
        inOrder.verify(view).hideLoading()
    }

}