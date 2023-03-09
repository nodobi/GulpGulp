package com.dohyeok.gulpgulp.view.home

import android.os.Bundle
import com.dohyeok.gulpgulp.databinding.HomeActivityBinding
import com.dohyeok.gulpgulp.view.base.BaseActivity

class HomeActivity : BaseActivity<HomeActivityBinding>({ HomeActivityBinding.inflate(it) }) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setSupportActionBar(binding.toolbarHome)
    }
}