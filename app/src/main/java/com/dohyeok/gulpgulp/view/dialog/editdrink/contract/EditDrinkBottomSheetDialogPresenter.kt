package com.dohyeok.gulpgulp.view.dialog.editdrink.contract

import android.view.View

class EditDrinkBottomSheetDialogPresenter(
    override var view: EditDrinkBottomSheetDialogContract.View
) : EditDrinkBottomSheetDialogContract.Presenter {
    override lateinit var onImageClick: (View) -> Unit
}