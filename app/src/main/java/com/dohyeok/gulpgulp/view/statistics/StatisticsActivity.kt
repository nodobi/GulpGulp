package com.dohyeok.gulpgulp.view.statistics

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.dohyeok.gulpgulp.R
import com.dohyeok.gulpgulp.databinding.ActivityStatisticsBinding
import com.dohyeok.gulpgulp.util.replaceFragment
import com.dohyeok.gulpgulp.util.startActivityExt
import com.dohyeok.gulpgulp.view.base.BaseActivity
import com.dohyeok.gulpgulp.view.main.MainActivity
import com.dohyeok.gulpgulp.view.statistics.contract.StatisticsContract
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class StatisticsActivity: BaseActivity<ActivityStatisticsBinding>({ ActivityStatisticsBinding.inflate(it) }) {
    @Inject
    lateinit var statisticsView: StatisticsContract.View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initToolbar()

        replaceFragment(binding.statisticsFramelayout.id, statisticsView as Fragment)
    }

    private fun initToolbar() {
        binding.toolbar.apply {
            setNavigationIcon(R.drawable.ic_navigation_24dp)
            setNavigationOnClickListener {
                startActivityExt(this@StatisticsActivity, MainActivity::class.java)
            }
        }
    }
}