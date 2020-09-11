package com.burakiren.marveldemo.main

import android.content.Context
import android.os.Bundle
import com.burakiren.domain.model.Hero
import com.burakiren.marveldemo.MarvelApp
import com.burakiren.marveldemo.R
import com.burakiren.marveldemo.base.BaseView
import com.burakiren.marveldemo.base.MvpPresenter
import com.burakiren.marveldemo.databinding.ActivityMainBinding
import com.burakiren.marveldemo.databinding.FragmentMainBinding
import com.burakiren.marveldemo.helper.OnItemClickListener
import com.burakiren.marveldemo.helper.RecyclerViewPaginator
import kotlinx.android.synthetic.main.fragment_main.*
import java.util.*
import javax.inject.Inject


class MarvelListView : BaseView(), MarvelListContract.View {

    private val binding: ActivityMainBinding? = null
    private var fragmentMainBinding: FragmentMainBinding? = null
    private var selectedCharacterId = 0


    @Inject
    lateinit var presenter: MarvelListPresenter

    lateinit var comicsAdapter: ComicsAdapter
    lateinit var heroesAdapter: HeroesAdapter


    override fun injectDependencies() {
        DaggerMarvelListComponent.builder()
            .appComponent(MarvelApp.component)
            .marvelListModule(MarvelListModule())
            .build()
            .inject(this)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        heroesAdapter =
            HeroesAdapter(
                emptyList<Hero>().toMutableList() as ArrayList<Hero>?,
                context,
                OnItemClickListener {
                    selectedCharacterId = it.id
                    fetchComics(it.id, 0)
                })
        rv_heroes.adapter = heroesAdapter

        comicsAdapter =
            ComicsAdapter(emptyList<Hero>().toMutableList() as ArrayList<Hero>?, context)
        rv_comics.adapter = comicsAdapter
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        with(presenter) {
            start(this@MarvelListView)
            loadHeroes(0)
        }

    }

    private fun populateData(heroes: List<Hero>) {
        heroesAdapter.setItems(heroes)
        rv_heroes.addOnScrollListener(object : RecyclerViewPaginator(rv_heroes) {
            override fun isLastPage(): Boolean {
                return false
            }

            override fun loadMore(start: Long?, count: Long?) {
                presenter.loadHeroes((count!!.toInt() / 20) * 5)
            }
        })
        fetchComics(heroes[0].id, 0)

    }

    private fun fetchComics(id: Int, offset: Int) {
        presenter.fetchComics(id, offset)
    }

    override fun onLoadHeroesSuccess(heroes: List<Hero>) {
        populateData(heroes)
    }

    override fun onLoadHeroesError(throwable: Throwable) {
        println(throwable.message)
    }

    override fun onLoadComicsSuccess(comics: List<Hero>) {

        comicsAdapter.setItems(comics)

        rv_comics.addOnScrollListener(object : RecyclerViewPaginator(rv_comics) {
            override fun isLastPage(): Boolean {
                return false
            }

            override fun loadMore(start: Long?, count: Long?) {
                fetchComics(id = selectedCharacterId, offset = (count!!.toInt() / 20) * 5)
            }
        })
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