package com.dohyeok.gulpgulp.view.home.contract

import android.view.MenuItem
import com.dohyeok.gulpgulp.view.base.BaseContract

interface HomeContract {
    interface View : BaseContract.View {
        var presenter: Presenter
    }

    interface Presenter : BaseContract.Presenter<View> {
        var onMenuClick: ((MenuItem) -> Boolean)
    }
}