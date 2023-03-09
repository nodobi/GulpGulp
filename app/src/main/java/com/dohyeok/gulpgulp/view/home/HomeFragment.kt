package com.dohyeok.gulpgulp.view.home

import android.view.LayoutInflater
import android.view.ViewGroup
import com.dohyeok.gulpgulp.databinding.HomeFragmentBinding
import com.dohyeok.gulpgulp.view.base.BaseFragment
import com.dohyeok.gulpgulp.view.home.contract.HomeContract

class HomeFragment : BaseFragment<HomeFragmentBinding>(), HomeContract.View {


    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): HomeFragmentBinding {
        return HomeFragmentBinding.inflate(inflater, container, true)
    }
}