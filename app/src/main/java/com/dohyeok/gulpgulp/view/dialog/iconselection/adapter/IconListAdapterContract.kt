package com.dohyeok.gulpgulp.view.dialog.iconselection.adapter

import com.dohyeok.gulpgulp.data.icon.Icon

interface IconListAdapterContract {
    interface View {
        var onIconClick: (Icon) -> Unit
        fun notifyIconsChange()
    }

    interface Model {
        fun loadIconData(iconList: List<Icon>)
    }
}