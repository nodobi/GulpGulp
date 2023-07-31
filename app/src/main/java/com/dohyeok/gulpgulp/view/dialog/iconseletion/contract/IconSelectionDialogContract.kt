package com.dohyeok.gulpgulp.view.dialog.iconseletion.contract

import com.dohyeok.gulpgulp.view.base.BaseContract
import com.dohyeok.gulpgulp.view.dialog.iconseletion.adapter.IconSelectionDialogAdapterContract

interface IconSelectionDialogContract {
    interface View : BaseContract.View

    interface Presenter : BaseContract.Presenter<View> {
        var iconAdapterView: IconSelectionDialogAdapterContract.View
        var iconAdapterModel: IconSelectionDialogAdapterContract.Model

        fun initIconIdData()
    }
}