package com.dohyeok.gulpgulp.view.setting

import android.annotation.SuppressLint
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
import com.dohyeok.gulpgulp.util.SPUtils
import com.dohyeok.gulpgulp.view.base.BaseFragment
import com.dohyeok.gulpgulp.view.editdrinkdetail.EditDrinkActivity
import com.dohyeok.gulpgulp.view.setting.contract.SettingContract
import com.dohyeok.gulpgulp.view.setting.contract.SettingPresenter
import com.google.android.material.bottomsheet.BottomSheetDialog

class SettingFragment : BaseFragment<SettingFragmentBinding>(), SettingContract.View {
    override lateinit var presenter: SettingPresenter
    private lateinit var goalBottomBinding: SettingGoalBottomDialogBinding
    private lateinit var goalBottomDialog: BottomSheetDialog

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
            SPUtils(requireContext())
        )
        goalBottomDialog = initGoalBottomSheetDialog()


        binding.apply {
            switchAlert.apply {
                isChecked = presenter.getAlarmEnabled()
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
        changeAlertVisibility(presenter.getAlarmEnabled())
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