package com.burakiren.marveldemo.main

import android.content.Context
import com.burakiren.domain.model.Hero
import com.burakiren.marveldemo.MarvelApp
import com.burakiren.marveldemo.R
import com.burakiren.marveldemo.base.BaseView
import com.burakiren.marveldemo.base.MvpPresenter
import com.burakiren.marveldemo.databinding.ActivityMainBinding
import com.burakiren.marveldemo.databinding.FragmentMainBinding
import kotlinx.android.synthetic.main.fragment_main.*
import javax.inject.Inject

class MarvelListView : BaseView(), MarvelListContract.View {

    private val binding: ActivityMainBinding? = null
    private var fragmentMainBinding: FragmentMainBinding? = null


    @Inject
    lateinit var presenter: MarvelListPresenter

    override fun injectDependencies() {
        DaggerMarvelListComponent.builder()
            .appComponent(MarvelApp.component)
            .marvelListModule(MarvelListModule())
            .build()
            .inject(this)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        with(presenter) {
            start(this@MarvelListView)
            loadHeroes()
        }
    }

    private fun populateData(heroes: List<Hero>) {

        val myRecyclerViewAdapter =
            HeroesAdapter(heroes, context)
        rv_heroes.adapter = myRecyclerViewAdapter
    }

    override fun onLoadHeroesSuccess(heroes: List<Hero>) {
        populateData(heroes)
    }

    override fun onLoadHeroesError(throwable: Throwable) {
        println(throwable.message)
    }

    override fun showLoading() {
        println("Show Loading")
    }

    override fun hideLoading() {
        println("Hide Loading")
    }

    override fun getLayoutId() = R.layout.fragment_main

    override fun getToolbarTitleId() = R.string.app_name

    override fun getPresenter(): MvpPresenter = presenter


}