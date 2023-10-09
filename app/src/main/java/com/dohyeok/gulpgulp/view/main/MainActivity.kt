package com.dohyeok.gulpgulp.view.main

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import com.dohyeok.gulpgulp.R
import com.dohyeok.gulpgulp.databinding.ActivityMainBinding
import com.dohyeok.gulpgulp.util.replaceFragment
import com.dohyeok.gulpgulp.util.startActivityExt
import com.dohyeok.gulpgulp.view.base.BaseActivity
import com.dohyeok.gulpgulp.view.main.contract.MainContract
import com.dohyeok.gulpgulp.view.main.contract.MainPresenter
import com.dohyeok.gulpgulp.view.setting.SettingActivity
import com.dohyeok.gulpgulp.view.statistics.StatisticsActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>({ActivityMainBinding.inflate(it)}) {

    @Inject
    lateinit var mainView: MainContract.View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initToolbar()

        replaceFragment(binding.mainFramelayout.id, mainView as Fragment)
    }
    private fun initToolbar() {
        binding.toolbar.apply {
            inflateMenu(R.menu.menu_main)
            setNavigationIcon(R.drawable.ic_calendar_24dp)
            setOnMenuItemClickListener { menuItem ->
                when(menuItem.itemId) {
                    R.id.menu_setting -> {
                        startActivityExt(this@MainActivity, SettingActivity::class.java)
                    }
                }
                true
            }
            setNavigationOnClickListener {
                startActivityExt(this@MainActivity, StatisticsActivity::class.java)
            }
        }
    }
}