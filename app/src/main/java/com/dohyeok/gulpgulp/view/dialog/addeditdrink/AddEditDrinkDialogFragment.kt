package com.dohyeok.gulpgulp.view.dialog.addeditdrink

import android.os.Build.VERSION
import android.os.Build.VERSION_CODES
import android.os.Bundle
import android.view.*
import android.widget.EditText
import android.widget.NumberPicker
import com.dohyeok.gulpgulp.R
import com.dohyeok.gulpgulp.data.drink.DrinkItem
import com.dohyeok.gulpgulp.data.icon.Icon
import com.dohyeok.gulpgulp.databinding.DialogAddeditDrinkBinding
import com.dohyeok.gulpgulp.view.base.BaseBottomSheetDialogFragment
import com.dohyeok.gulpgulp.view.dialog.addeditdrink.contract.AddEditDrinkDialogContract
import com.dohyeok.gulpgulp.view.dialog.iconselection.IconSelectionDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalTime
import javax.inject.Inject

@AndroidEntryPoint
class AddEditDrinkDialogFragment : BaseBottomSheetDialogFragment<DialogAddeditDrinkBinding>(), AddEditDrinkDialogContract.View {
    @Inject
    lateinit var presenter: AddEditDrinkDialogContract.Presenter

    lateinit var onCommit: (DrinkItem, LocalTime) -> Unit
    private var targetDrink: DrinkItem? = null
    var isEditRecord = false

    override fun initFragmentViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): DialogAddeditDrinkBinding {
        setStyle(STYLE_NORMAL, R.style.BottomSheetDialogStyle)
        return DialogAddeditDrinkBinding.inflate(layoutInflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.attachView(this)


        binding.apply {
            btnCommit.setOnClickListener {
                presenter.refreshDrink()
                onCommit.invoke(presenter.getDrink(), getPickersTime())
            }
            if(isEditRecord) {
                groupPickers.visibility = View.VISIBLE
            } else {
                groupPickers.visibility = View.GONE
            }
        }

        if(VERSION.SDK_INT > VERSION_CODES.R) {
            dialog?.window?.setDecorFitsSystemWindows(false)
        } else {
            dialog?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        }

        presenter.initDrink(targetDrink)
        initPickers()

    }

    override fun updateDrinkDetails(drinkItem: DrinkItem, iconResId: Int, withHints: Boolean) {
        binding.apply {
            imgDrinkIcon.apply {
                setImageResource(iconResId)
                setOnClickListener {
                    showIconSelectionDialog()
                }
            }
            if(withHints) {
                edittextDrinkName.hint = drinkItem.name
                edittextDrinkAmount.hint = drinkItem.amount.toString()
            } else {
                edittextDrinkName.setText(drinkItem.name)
                edittextDrinkAmount.setText(drinkItem.amount.toString())
            }
        }
    }

    override fun updateDrinkIcons(newIcon: Icon) {
        binding.imgDrinkIcon.setImageResource(newIcon.resId)
    }

    override fun getDrinkAmount(): String {
        return binding.edittextDrinkName.text.toString()
    }

    override fun getDrinkName(): String {
        return binding.edittextDrinkName.text.toString()
    }

    fun setTargetDrink(drinkItem: DrinkItem) {
        targetDrink = drinkItem
    }

    private fun getPickersTime(): LocalTime {
        return LocalTime.of(binding.numberpickerRecordHour.value, binding.numberpickerRecordMin.value, binding.numberpickerRecordSec.value)
    }

    private fun showIconSelectionDialog() {
        IconSelectionDialogFragment().apply {
            onCommit = {
                it?.let {
                    this@AddEditDrinkDialogFragment.presenter.onIconDialogCommit(it)
                }
                dismiss()
            }
            onDismiss = {
                dismiss()
            }

        }.show(parentFragmentManager, "iconSelectionDialog")
    }

    private fun initPickers() {
        val hourMinValue = 0
        val hourMaxValue = 23
        val minMinValue = 0
        val minMaxValue = 59
        val secMinValue = 0
        val secMaxValue = 59
        binding.apply {
            numberpickerRecordHour.apply {
                minValue = hourMinValue
                maxValue = hourMaxValue
                setFormatter { it.toString() + "시" }
            }
            numberpickerRecordMin.apply {
                minValue = minMinValue
                maxValue = minMaxValue
                setFormatter { it.toString() + "분" }
            }
            numberpickerRecordSec.apply {
                minValue = secMinValue
                maxValue = secMaxValue
                setFormatter { it.toString() + "초" }
            }
        }
        // 처음 항목이 안보이는 버그 해결
        val f = NumberPicker::class.java.getDeclaredField("mInputText")
        f.isAccessible = true
        (f.get(binding.numberpickerRecordHour) as EditText).filters = arrayOfNulls(0)
        (f.get(binding.numberpickerRecordMin) as EditText).filters = arrayOfNulls(0)
        (f.get(binding.numberpickerRecordSec) as EditText).filters = arrayOfNulls(0)

    }
}