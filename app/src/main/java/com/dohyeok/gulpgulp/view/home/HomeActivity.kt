package com.dohyeok.gulpgulp.view.home

import android.os.Bundle
import com.dohyeok.gulpgulp.databinding.HomeActivityBinding
import com.dohyeok.gulpgulp.util.replaceFragment
import com.dohyeok.gulpgulp.view.base.BaseActivity
import com.dohyeok.gulpgulp.view.home.contract.HomePresenter

class HomeActivity : BaseActivity<HomeActivityBinding>({ HomeActivityBinding.inflate(it) }) {
    private lateinit var homeFragment: HomeFragment
    private lateinit var homePresenter: HomePresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        homeFragment = HomeFragment()
        homePresenter = HomePresenter().apply {
            view = homeFragment
        }
        homeFragment.presenter = homePresenter

        binding.toolbarHome.apply {
            setOnMenuItemClickListener(homePresenter.onMenuClick)
            setNavigationOnClickListener(homePresenter.onNavigationClick)
        }

        replaceFragment(binding.frameContent.id, homeFragment)
    }
}