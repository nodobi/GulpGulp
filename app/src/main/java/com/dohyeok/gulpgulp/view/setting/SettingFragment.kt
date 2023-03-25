package com.dohyeok.gulpgulp.view.setting

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dohyeok.gulpgulp.databinding.SettingFragmentBinding
import com.dohyeok.gulpgulp.view.base.BaseFragment
import com.dohyeok.gulpgulp.view.editdrinkdetail.EditDrinkActivity
import com.dohyeok.gulpgulp.view.setting.contract.SettingContract
import com.dohyeok.gulpgulp.view.setting.contract.SettingPresenter

class SettingFragment : BaseFragment<SettingFragmentBinding>(), SettingContract.View {
    override lateinit var presenter: SettingPresenter
    lateinit var preferences: SharedPreferences

    companion object {
        const val PREFERENCE_NAME = "PREFERENCE_NAME"
        const val PREFERENCE_KEY_ALERT = "PREFERENCE_KEY_ALERT"
    }

    override fun getFragmentBinding(
        inflater: LayoutInflater, container: ViewGroup?
    ): SettingFragmentBinding {
        return SettingFragmentBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        preferences = requireContext().getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)
        presenter = SettingPresenter(this, preferences)

        binding.switchAlert.apply {
            changeAlertVisibility(preferences.getBoolean(PREFERENCE_KEY_ALERT, false))
            setOnCheckedChangeListener { _, isChecked ->
                presenter.onAlertChanged.invoke(isChecked)
            }
        }

        binding.viewDrinkFrame.setOnClickListener {
            startActivity(Intent(requireActivity(), EditDrinkActivity::class.java))
        }
    }

    override fun changeAlertVisibility(isVisible: Boolean) {
        if (isVisible) {
            binding.groupAlertDetail.visibility = View.VISIBLE
        } else {
            binding.groupAlertDetail.visibility = View.GONE
        }
    }
}