package com.dohyeok.gulpgulp.view.setting.contract

import com.dohyeok.gulpgulp.view.base.BaseContract

interface SettingContract {
    interface View : BaseContract.View {
        var presenter: SettingPresenter

        fun changeAlertVisibility(isVisible: Boolean)
    }

    interface Presenter : BaseContract.Presenter<View> {
        var onAlertChanged: (Boolean) -> Unit
    }
}