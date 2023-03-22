package com.dohyeok.gulpgulp.view.home

import android.os.Bundle
import com.dohyeok.gulpgulp.R
import com.dohyeok.gulpgulp.databinding.HomeActivityBinding
import com.dohyeok.gulpgulp.util.replaceFragment
import com.dohyeok.gulpgulp.util.startActivityWithoutIntent
import com.dohyeok.gulpgulp.view.base.BaseActivity
import com.dohyeok.gulpgulp.view.calendar.CalendarActivity
import com.dohyeok.gulpgulp.view.setting.SettingActivity

class HomeActivity : BaseActivity<HomeActivityBinding>({ HomeActivityBinding.inflate(it) }) {
    private lateinit var homeFragment: HomeFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        homeFragment = HomeFragment()
        binding.toolbar.view.apply {
            inflateMenu(R.menu.menu_home_toolbar)
            setOnMenuItemClickListener {
                if (it.itemId == R.id.item_option) {
                    onOptionClick()
                }
                true
            }
            setNavigationIcon(R.drawable.ic_calendar_24dp)
            setNavigationOnClickListener {
                onNavigationClick()
            }
        }

        replaceFragment(binding.frameContent.id, homeFragment)
    }

    private fun onNavigationClick() {
        startActivityWithoutIntent(this, CalendarActivity::class.java)
    }

    private fun onOptionClick() {
        startActivityWithoutIntent(this, SettingActivity::class.java)
    }

}