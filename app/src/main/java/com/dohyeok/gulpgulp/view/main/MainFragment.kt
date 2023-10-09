package com.dohyeok.gulpgulp.view.main

import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dohyeok.gulpgulp.R
import com.dohyeok.gulpgulp.databinding.FragmentMainBinding
import com.dohyeok.gulpgulp.util.dpToPx
import com.dohyeok.gulpgulp.view.base.BaseFragment
import com.dohyeok.gulpgulp.view.main.adapter.DrinkListAdapter
import com.dohyeok.gulpgulp.view.main.contract.MainContract
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainFragment : BaseFragment<FragmentMainBinding>(), MainContract.View {
    @Inject
    lateinit var presenter: MainContract.Presenter

    override fun initFragmentViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentMainBinding {
        return FragmentMainBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        presenter.attachView(this)

        binding.recyclerDrinkList.apply {
            adapter = presenter.drinkListModel as DrinkListAdapter
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            addItemDecoration(HorizonSpacingDecoration())
        }

        presenter.loadDrinkItemData()
        presenter.loadTodayGoalData()

    }

    override fun updateDrinkAmount(amount: Int) {
        binding.drinkAmount.text =
        requireContext().resources.getString(R.string.drink_format_ml, amount)
    }

    override fun updateDrinkGoal(amount: Int) {
        binding.drinkAmount.text = amount.toString()
    }

    override fun updateDrinkProgressbar(max: Int, progress: Int) {
        binding.drinkProgressbar.max = max
        binding.drinkProgressbar.setProgress(progress, true)
    }

    inner class HorizonSpacingDecoration : RecyclerView.ItemDecoration() {
        override fun getItemOffsets(
            outRect: Rect,
            view: View,
            parent: RecyclerView,
            state: RecyclerView.State
        ) {
            super.getItemOffsets(outRect, view, parent, state)
            if (parent.adapter != null) {
                val spacing = if (parent.getChildAdapterPosition(view) == 0)
                    (binding.drinkProgressbar.layoutParams.width / 2)
                else
                    dpToPx(
                        requireContext(),
                        resources.getDimension(R.dimen.margin_padding_size_small)
                    ).toInt()
                outRect.left = spacing
            }
        }
    }
}