package com.dohyeok.gulpgulp.view.dialog.editdrink.contract

import com.dohyeok.gulpgulp.data.Drink
import com.dohyeok.gulpgulp.view.base.BaseContract
import java.time.LocalTime

interface EditDrinkBottomSheetDialogContract {
    interface View : BaseContract.View {
        var onCommit: (Drink, LocalTime) -> Unit
    }

    interface Presenter : BaseContract.Presenter<View> {
        var onImageClick: (android.view.View) -> Unit
    }
}