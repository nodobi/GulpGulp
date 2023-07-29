package com.dohyeok.gulpgulp.view.editdrinkdetail.adapter

interface IconSelectionDialogAdapterContract {
    interface View {
        var onItemClick: (Int) -> (Unit)
    }
    interface Model {
        var iconIdData: MutableList<Int>
    }
}