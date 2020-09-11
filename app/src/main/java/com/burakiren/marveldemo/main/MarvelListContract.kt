package com.burakiren.marveldemo.main

import com.burakiren.domain.model.Hero

interface MarvelListContract {

    interface View {
        fun onLoadHeroesSuccess(heroes: List<Hero>)
        fun onLoadComicsSuccess(comics: List<Hero>)
        fun onLoadHeroesError(throwable: Throwable)
        fun showLoading()
        fun hideLoading()
    }

    interface Presenter {
        fun loadHeroes()
        fun fetchComics(id: Int)
    }
}