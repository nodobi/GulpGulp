package com.dohyeok.gulpgulp.view.setting

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.dohyeok.gulpgulp.R
import com.dohyeok.gulpgulp.databinding.ActivitySettingBinding
import com.dohyeok.gulpgulp.util.replaceFragment
import com.dohyeok.gulpgulp.util.startActivityExt
import com.dohyeok.gulpgulp.view.base.BaseActivity
import com.dohyeok.gulpgulp.view.main.MainActivity
import com.dohyeok.gulpgulp.view.setting.contract.SettingContract
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SettingActivity: BaseActivity<ActivitySettingBinding>({ ActivitySettingBinding.inflate(it) }) {

    @Inject
    lateinit var settingView: SettingContract.View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initToolbar()
        replaceFragment(binding.settingFramelayout.id, settingView as Fragment)
    }

    private fun initToolbar() {
        binding.toolbar.apply {
            setNavigationIcon(R.drawable.ic_navigation_24dp)
            setNavigationOnClickListener {
                startActivityExt(this@SettingActivity, MainActivity::class.java)
            }
        }
    }
}