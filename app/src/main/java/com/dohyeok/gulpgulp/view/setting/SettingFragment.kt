package com.dohyeok.gulpgulp.view.setting

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dohyeok.gulpgulp.R
import com.dohyeok.gulpgulp.data.drink.DrinkRepository
import com.dohyeok.gulpgulp.data.goal.GoalRepository
import com.dohyeok.gulpgulp.databinding.FragmentSettingBinding
import com.dohyeok.gulpgulp.view.base.BaseFragment
import com.dohyeok.gulpgulp.view.dialog.goalpicker.GoalPickerDialogFragment
import com.dohyeok.gulpgulp.view.managedrink.ManageDrinkActivity
import com.dohyeok.gulpgulp.view.setting.contract.SettingContract
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SettingFragment : BaseFragment<FragmentSettingBinding>(), SettingContract.View {
    @Inject
    lateinit var presenter: SettingContract.Presenter
    @Inject
    lateinit var drinkRepository: DrinkRepository
    @Inject
    lateinit var goalRepository: GoalRepository

    override fun initFragmentViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentSettingBinding {
        return FragmentSettingBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        presenter.attachView(this)
        presenter.loadGoalAmount()

        binding.viewOptionManageDrink.setOnClickListener {
            onSettingOptionClicked(it.id)
        }
        binding.viewOptionGoal.setOnClickListener {
            onSettingOptionClicked(it.id)
        }
    }

    override fun updateGoalAmount(goal: Int) {
        binding.amountOptionGoal.text = requireContext().resources.getString(R.string.drink_format_ml, goal)
    }


    private fun onSettingOptionClicked(clickedViewId: Int) {
        when (clickedViewId) {
            binding.viewOptionManageDrink.id -> {
                startActivity(Intent(requireActivity(), ManageDrinkActivity::class.java))
            }
            binding.viewOptionGoal.id -> {
                onOptionGoalClicked()
            }
        }
    }

    private fun onOptionGoalClicked() {
        GoalPickerDialogFragment().apply {
            onCommit = {
                Log.d("DHK", "onCommit| value: $it")
                this@SettingFragment.presenter.onGoalPickerCommit(it)
                dismiss()
            }
        }.show(childFragmentManager, "GoalPickerDialog")
    }
}