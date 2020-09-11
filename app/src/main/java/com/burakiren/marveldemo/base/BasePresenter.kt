package com.burakiren.marveldemo.base

import io.reactivex.disposables.CompositeDisposable

abstract class BasePresenter<V : BaseView> : MvpPresenter {

    val disposables: CompositeDisposable = CompositeDisposable()
    var view: V? = null
        private set

    fun start(view: V) {
        this.view = view
    }

    override fun stop() {
        this.view = null
    }

    override fun destroy() {
        disposables.clear()
    }
}