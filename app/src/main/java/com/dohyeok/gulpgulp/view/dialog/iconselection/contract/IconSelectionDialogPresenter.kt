package com.dohyeok.gulpgulp.view.dialog.iconselection.contract

import com.dohyeok.gulpgulp.data.icon.Icon
import com.dohyeok.gulpgulp.data.icon.IconRepository
import com.dohyeok.gulpgulp.di.module.UIDispatcher
import com.dohyeok.gulpgulp.view.dialog.iconselection.adapter.IconListAdapterContract
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject


// TODO("아이콘 선택 시 배경색으로 선택한 아이콘 보여주는 기능 필요, 혹은 Commit, Dismiss 버튼 없이 이미지 선택하면 바로 되도록 변경")
class IconSelectionDialogPresenter @Inject constructor(
    override var iconListAdapterView: IconListAdapterContract.View,
    override var iconListAdapterModel: IconListAdapterContract.Model,
    private var iconRepository: IconRepository,
    @UIDispatcher private var uiDispatcher: CoroutineDispatcher
) : IconSelectionDialogContract.Presenter {
    override var _view: IconSelectionDialogContract.View? = null
    override var onCommit: (Unit) -> Unit = { onCommitLBtnListener() }
    override var onDismiss: (Unit) -> Unit = { onDismissBtnListener() }
    private var selectedIcon: Icon? = null
    init {
        iconListAdapterView.onIconClick = { onIconClickListener(it) }
    }

    override fun loadIcons() {
        CoroutineScope(uiDispatcher).launch {
            iconListAdapterModel.loadIconData(iconRepository.getAllIcons().values.toList())
            iconListAdapterView.notifyIconsChange()
        }
    }

    private fun onCommitLBtnListener() {
        view.onCommit(selectedIcon)
    }

    private fun onDismissBtnListener() {
        view.onDismiss(Unit)
    }

    private fun onIconClickListener(icon: Icon) {
        selectedIcon = icon
    }

}