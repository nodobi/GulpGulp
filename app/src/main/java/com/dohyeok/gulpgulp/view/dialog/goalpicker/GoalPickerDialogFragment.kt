package com.dohyeok.gulpgulp.view.dialog.goalpicker

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.NumberPicker
import com.dohyeok.gulpgulp.databinding.DialogGoalpickerBinding
import com.dohyeok.gulpgulp.util.SPUtil
import com.dohyeok.gulpgulp.view.base.BaseBottomSheetDialogFragment
import com.dohyeok.gulpgulp.view.dialog.goalpicker.contract.GoalPickerDialogContract
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class GoalPickerDialogFragment: BaseBottomSheetDialogFragment<DialogGoalpickerBinding>(), GoalPickerDialogContract.View {
    lateinit var onCommit: (Int) -> Unit

    @Inject
    lateinit var presenter: GoalPickerDialogContract.Presenter

    override fun initFragmentViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): DialogGoalpickerBinding {
        return DialogGoalpickerBinding.inflate(inflater, container, false)
    }

    @SuppressLint("DiscouragedPrivateApi")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        presenter.attachView(this)
        presenter.loadPickerValues()

        binding.btnCommit.setOnClickListener {
            onCommit(presenter.toFormatValue(binding.pickerGoal.value))
            presenter.saveGoalValue(binding.pickerGoal.value)
        }

        // 처음 항목이 안보이는 버그 해결
        val f = NumberPicker::class.java.getDeclaredField("mInputText")
        f.isAccessible = true
        (f.get(binding.pickerGoal) as EditText).filters = arrayOfNulls(0)

    }

    override fun setPickerValues(
        min: Int,
        max: Int,
        current: Int,
        formatter: NumberPicker.Formatter
    ) {
        binding.pickerGoal.apply {
            minValue = min
            maxValue = max
            value = current
            setFormatter(formatter)
        }
    }
}