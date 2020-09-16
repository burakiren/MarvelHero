package com.burakiren.marveldemo.ui.marvel

import com.burakiren.domain.usecase.GetComicsUseCase
import com.burakiren.domain.usecase.GetHeroesUseCase
import com.burakiren.marveldemo.base.BasePresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MarvelListPresenter @Inject constructor(
    private val getHeroesUseCase: GetHeroesUseCase,
    private val getComicsUseCase: GetComicsUseCase
) : BasePresenter<MarvelListView>(), MarvelListContract.Presenter {


    override fun loadHeroes(offset: Int) {
        disposables.add(getHeroesUseCase.getAllHeroes(offset)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { view?.showLoading() }
            .doFinally { view?.hideLoading() }
            .subscribe({ view?.onLoadHeroesSuccess(it) }, { view?.onLoadHeroesError(it) })
        )
    }

    override fun fetchComics(id: Int, offset: Int) {
        disposables.add(getComicsUseCase.getAllComics(id, offset)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { view?.showLoading() }
            .doFinally { view?.hideLoading() }
            .subscribe({ view?.onLoadComicsSuccess(it) }, { view?.onLoadHeroesError(it) })
        )
    }

}