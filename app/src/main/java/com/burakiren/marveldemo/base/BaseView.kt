package com.burakiren.marveldemo.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment

abstract class BaseView : Fragment() {

    val inject by lazy { injectDependencies() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(getLayoutId(), container, false)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        inject
    }

    override fun onDetach() {
        super.onDetach()
        getPresenter().stop()
    }


    override fun onDestroy() {
        getPresenter().destroy()
        super.onDestroy()
    }

    @LayoutRes
    protected abstract fun getLayoutId(): Int

    @StringRes
    protected abstract fun getToolbarTitleId(): Int

    protected abstract fun injectDependencies()

    protected abstract fun getPresenter(): MvpPresenter
}