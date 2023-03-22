package com.dohyeok.gulpgulp.view.setting

import android.os.Bundle
import com.dohyeok.gulpgulp.R
import com.dohyeok.gulpgulp.databinding.SettingActivityBinding
import com.dohyeok.gulpgulp.util.replaceFragment
import com.dohyeok.gulpgulp.util.startActivityWithoutIntent
import com.dohyeok.gulpgulp.view.base.BaseActivity
import com.dohyeok.gulpgulp.view.home.HomeActivity

class SettingActivity :
    BaseActivity<SettingActivityBinding>({ SettingActivityBinding.inflate(it) }) {
    private lateinit var settingFragment: SettingFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        settingFragment = SettingFragment()
        binding.toolbar.view.apply {
            setNavigationIcon(R.drawable.ic_back_24dp)
            setNavigationOnClickListener {
                onNavigationClick()
            }
        }

        replaceFragment(binding.frameContent.id, settingFragment)
    }

    private fun onNavigationClick() {
        startActivityWithoutIntent(this, HomeActivity::class.java)
    }
}