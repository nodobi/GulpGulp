package com.dohyeok.gulpgulp.view.home.contract

import android.view.MenuItem

class HomePresenter : HomeContract.Presenter {
    override lateinit var view: HomeContract.View
    override var onMenuClick: (MenuItem) -> Boolean = { onMenuClickListener(it) }


    private fun onMenuClickListener(menuItem: MenuItem): Boolean {
        return true
    }
}