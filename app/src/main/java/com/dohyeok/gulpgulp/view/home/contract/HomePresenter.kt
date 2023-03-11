package com.dohyeok.gulpgulp.view.home.contract

import android.view.MenuItem
import android.view.View

class HomePresenter : HomeContract.Presenter {
    override lateinit var view: HomeContract.View
    override var onNavigationClick: (View) -> Unit = { onNavigationClickListener(it) }
    override var onMenuClick: (MenuItem) -> Boolean = { onMenuClickListener(it) }

    private fun onNavigationClickListener(view: View) {

    }

    private fun onMenuClickListener(menuItem: MenuItem): Boolean {
        return true
    }
}