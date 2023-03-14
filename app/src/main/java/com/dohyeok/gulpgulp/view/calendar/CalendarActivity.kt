package com.dohyeok.gulpgulp.view.calendar

import android.os.Bundle
import com.dohyeok.gulpgulp.R
import com.dohyeok.gulpgulp.databinding.CalendarActivityBinding
import com.dohyeok.gulpgulp.util.replaceFragment
import com.dohyeok.gulpgulp.view.base.BaseActivity
import com.dohyeok.gulpgulp.view.calendar.contract.CalendarPresenter

class CalendarActivity: BaseActivity<CalendarActivityBinding>({ CalendarActivityBinding.inflate(it)}) {
    private lateinit var calendarFragment: CalendarFragment
    private lateinit var calendarPresenter: CalendarPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        calendarFragment = CalendarFragment()
        calendarPresenter = CalendarPresenter().apply {
            view = calendarFragment
        }
        calendarFragment.presenter = calendarPresenter


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