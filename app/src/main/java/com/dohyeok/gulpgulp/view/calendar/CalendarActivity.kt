package com.dohyeok.gulpgulp.view.calendar

import android.os.Bundle
import com.dohyeok.gulpgulp.R
import com.dohyeok.gulpgulp.databinding.CalendarActivityBinding
import com.dohyeok.gulpgulp.util.replaceFragment
import com.dohyeok.gulpgulp.view.base.BaseActivity

class CalendarActivity: BaseActivity<CalendarActivityBinding>({ CalendarActivityBinding.inflate(it)}) {
    private lateinit var calendarFragment: CalendarFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        calendarFragment = CalendarFragment()

        binding.toolbar.view.apply {
            setNavigationIcon(R.drawable.ic_arrow_back_24dp)
            setNavigationOnClickListener {
                onNavigationClick()
            }
        }

        replaceFragment(binding.frameContent.id, calendarFragment)
    }

    private fun onNavigationClick() {
        finish()
    }
}