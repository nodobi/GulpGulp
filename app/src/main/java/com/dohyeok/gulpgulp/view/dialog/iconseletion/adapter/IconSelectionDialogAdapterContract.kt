package com.dohyeok.gulpgulp.view.dialog.iconseletion.adapter

interface IconSelectionDialogAdapterContract {
    interface View {
        var onItemClick: (Int, String) -> Unit
    }

    interface Model {
        var iconIdData: MutableList<Int>
    }
}