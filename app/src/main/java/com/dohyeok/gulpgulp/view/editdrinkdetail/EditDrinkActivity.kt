package com.dohyeok.gulpgulp.view.editdrinkdetail

import android.os.Bundle
import com.dohyeok.gulpgulp.R
import com.dohyeok.gulpgulp.databinding.EditdrinkActivityBinding
import com.dohyeok.gulpgulp.util.replaceFragment
import com.dohyeok.gulpgulp.util.startActivityWithoutIntent
import com.dohyeok.gulpgulp.view.base.BaseActivity
import com.dohyeok.gulpgulp.view.setting.SettingActivity

class EditDrinkActivity: BaseActivity<EditdrinkActivityBinding>({ EditdrinkActivityBinding.inflate(it)}) {
    lateinit var editDrinkFragment: EditDrinkFragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        editDrinkFragment = EditDrinkFragment()
        binding.toolbar.view.apply {
            setNavigationIcon(R.drawable.ic_back_24dp)
            setNavigationOnClickListener {
                onNavigationClick()
            }
        }

        replaceFragment(binding.frameContent.id, editDrinkFragment)
    }

    private fun onNavigationClick() {
        startActivityWithoutIntent(this, SettingActivity::class.java)
    }
}