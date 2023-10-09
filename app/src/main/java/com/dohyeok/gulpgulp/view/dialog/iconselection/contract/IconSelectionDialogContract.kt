package com.dohyeok.gulpgulp.view.dialog.iconselection.contract

import com.dohyeok.gulpgulp.data.icon.Icon
import com.dohyeok.gulpgulp.view.base.BaseContract
import com.dohyeok.gulpgulp.view.dialog.iconselection.adapter.IconListAdapterContract

interface IconSelectionDialogContract {
    interface View: BaseContract.View {
        var onCommit: (Icon?) -> Unit
        var onDismiss: (Unit) -> Unit
    }
    interface Presenter: BaseContract.Presenter<View> {
        var iconListAdapterView: IconListAdapterContract.View
        var iconListAdapterModel: IconListAdapterContract.Model
        var onCommit: (Unit) -> Unit
        var onDismiss: (Unit) -> Unit

        fun loadIcons()
    }
}