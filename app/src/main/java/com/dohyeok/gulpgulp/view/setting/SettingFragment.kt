package com.dohyeok.gulpgulp.view.setting

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.NumberPicker
import com.dohyeok.gulpgulp.R
import com.dohyeok.gulpgulp.databinding.SettingFragmentBinding
import com.dohyeok.gulpgulp.databinding.SettingGoalBottomDialogBinding
import com.dohyeok.gulpgulp.view.base.BaseFragment
import com.dohyeok.gulpgulp.view.editdrinkdetail.EditDrinkActivity
import com.dohyeok.gulpgulp.view.setting.contract.SettingContract
import com.dohyeok.gulpgulp.view.setting.contract.SettingPresenter
import com.google.android.material.bottomsheet.BottomSheetDialog

class SettingFragment : BaseFragment<SettingFragmentBinding>(), SettingContract.View {
    override lateinit var presenter: SettingPresenter
    private lateinit var goalBottomBinding: SettingGoalBottomDialogBinding
    private lateinit var goalBottomDialog: BottomSheetDialog

    companion object {
        const val PREFERENCE_NAME = "PREFERENCE_NAME"
        const val PREFERENCE_KEY_ALERT = "PREFERENCE_KEY_ALERT"
        const val PREFERENCE_KEY_GOAL = "PREFERENCE_KEY_GOAL"
    }

    override fun getFragmentBinding(
        inflater: LayoutInflater, container: ViewGroup?
    ): SettingFragmentBinding {
        return SettingFragmentBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        goalBottomBinding =
            SettingGoalBottomDialogBinding.inflate(layoutInflater, binding.root, false)
        presenter = SettingPresenter(
            this,
            requireContext().getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)
        )

        binding.apply {
            switchAlert.apply {
                changeAlertVisibility(presenter.getPreferenceValue(PREFERENCE_KEY_ALERT) as Boolean)
                setOnCheckedChangeListener { _, isChecked ->
                    presenter.onAlertChanged.invoke(isChecked)
                }
            }
            viewDrinkFrame.setOnClickListener {
                startActivity(Intent(requireActivity(), EditDrinkActivity::class.java))
            }
            viewDrinkGoalFrame.setOnClickListener {
                goalBottomDialog.show()
            }
        }

        changeDisplayDrinkGoal(presenter.getDrinkGoal())

        goalBottomDialog = initGoalBottomSheetDialog()

    }

    override fun changeAlertVisibility(isVisible: Boolean) {
        if (isVisible) {
            binding.groupAlertDetail.visibility = View.VISIBLE
        } else {
            binding.groupAlertDetail.visibility = View.GONE
        }
    }

    override fun changeDisplayDrinkGoal(goal: Int) {
        binding.textDrinkGoal.text = resources.getString(R.string.unit_ml, goal)
    }

    @SuppressLint("DiscouragedPrivateApi")
    private fun initGoalBottomSheetDialog(): BottomSheetDialog {
        goalBottomBinding.apply {
            numberpickerDrinkAmount.apply {
                setFormatter {
                    resources.getString(
                        R.string.unit_ml, it * presenter.goalNumberPickerNumberMultiply
                    )
                }
                minValue = 1
                maxValue = 100
                value = presenter.getDrinkGoal() / presenter.goalNumberPickerNumberMultiply
            }
            btnCommit.setOnClickListener {
                presenter.onGoalCommitBtnClick.invoke(goalBottomBinding.numberpickerDrinkAmount.value)
                goalBottomDialog.dismiss()
            }
        }

        NumberPicker::class.java.getDeclaredField("mInputText").apply {
            isAccessible = true
            (get(goalBottomBinding.numberpickerDrinkAmount) as EditText).filters = arrayOfNulls(0)
        }

        return BottomSheetDialog(requireContext()).apply {
            setContentView(goalBottomBinding.root)
        }
    }
}