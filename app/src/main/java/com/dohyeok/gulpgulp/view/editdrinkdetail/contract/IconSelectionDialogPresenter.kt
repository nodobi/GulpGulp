package com.dohyeok.gulpgulp.view.editdrinkdetail.contract

import com.dohyeok.gulpgulp.R
import com.dohyeok.gulpgulp.view.editdrinkdetail.adapter.IconSelectionDialogAdapterContract

class IconSelectionDialogPresenter(
    override var view: IconSelectionDialogContract.View,
    override var iconAdapterView: IconSelectionDialogAdapterContract.View,
    override var iconAdapterModel: IconSelectionDialogAdapterContract.Model,
): IconSelectionDialogContract.Presenter {

    override fun initIconIdData() {
        iconAdapterModel.iconIdData = mutableListOf<Int>().apply {
            add(R.drawable.ic_water)
            add(R.drawable.ic_cocktail)
        }
    }

}