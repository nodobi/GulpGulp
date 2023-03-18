package com.dohyeok.gulpgulp.view.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.dohyeok.gulpgulp.data.source.drink.DrinkRepository
import com.dohyeok.gulpgulp.data.source.drink.local.DrinkDatabase
import com.dohyeok.gulpgulp.data.source.drink.local.DrinkLocalDataSource
import com.dohyeok.gulpgulp.databinding.HomeFragmentBinding
import com.dohyeok.gulpgulp.view.base.BaseFragment
import com.dohyeok.gulpgulp.view.home.adapter.HomeDrinkAdapter
import com.dohyeok.gulpgulp.view.home.contract.HomeContract
import com.dohyeok.gulpgulp.view.home.contract.HomePresenter

class HomeFragment : BaseFragment<HomeFragmentBinding>(), HomeContract.View {
    override lateinit var presenter: HomeContract.Presenter
    lateinit var adapter: HomeDrinkAdapter

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): HomeFragmentBinding {
        return HomeFragmentBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        adapter = HomeDrinkAdapter(requireContext())

        presenter = HomePresenter(this, adapter, adapter, DrinkRepository.apply {
            drinkLocalDataSource = DrinkLocalDataSource.apply {
                drinkDao = DrinkDatabase.getInstance(requireContext()).drinkDao()
            }
        })

        presenter.updateDrinkData()

        binding.recyclerHomeDrink.apply {
            adapter = this@HomeFragment.adapter
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        }
    }
}