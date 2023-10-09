package com.dohyeok.gulpgulp.view.managedrink

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.dohyeok.gulpgulp.R
import com.dohyeok.gulpgulp.databinding.ActivityManagedrinkBinding
import com.dohyeok.gulpgulp.util.replaceFragment
import com.dohyeok.gulpgulp.util.startActivityExt
import com.dohyeok.gulpgulp.view.base.BaseActivity
import com.dohyeok.gulpgulp.view.managedrink.contract.ManageDrinkContract
import com.dohyeok.gulpgulp.view.setting.SettingActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ManageDrinkActivity: BaseActivity<ActivityManagedrinkBinding>({ ActivityManagedrinkBinding.inflate(it)}) {

    @Inject
    lateinit var manageDrinkView: ManageDrinkContract.View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initToolbar()
        replaceFragment(binding.managedrinkFramelayout.id, manageDrinkView as Fragment)
    }

    private fun initToolbar() {
        binding.toolbar.apply {
            setNavigationIcon(R.drawable.ic_navigation_24dp)
            setNavigationOnClickListener {
                startActivityExt(this@ManageDrinkActivity, SettingActivity::class.java)
            }
        }
    }
}