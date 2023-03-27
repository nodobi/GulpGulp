package com.dohyeok.gulpgulp.view.calendar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.dohyeok.gulpgulp.R
import com.dohyeok.gulpgulp.data.source.drink.DrinkRepository
import com.dohyeok.gulpgulp.data.source.drink.local.DrinkDatabase
import com.dohyeok.gulpgulp.data.source.drink.local.DrinkLocalDataSource
import com.dohyeok.gulpgulp.databinding.CalendarFragmentBinding
import com.dohyeok.gulpgulp.util.SPUtils
import com.dohyeok.gulpgulp.util.yearMonthDateKrFormat
import com.dohyeok.gulpgulp.util.yearMonthKrFormat
import com.dohyeok.gulpgulp.view.base.BaseFragment
import com.dohyeok.gulpgulp.view.calendar.adapter.CalendarAdapter
import com.dohyeok.gulpgulp.view.calendar.adapter.CalendarDetailAdapter
import com.dohyeok.gulpgulp.view.calendar.contract.CalendarContract
import com.dohyeok.gulpgulp.view.calendar.contract.CalendarPresenter
import java.time.LocalDate

class CalendarFragment : BaseFragment<CalendarFragmentBinding>(), CalendarContract.View {
    private lateinit var adapter: CalendarAdapter
    private lateinit var detailAdapter: CalendarDetailAdapter
    lateinit var presenter: CalendarPresenter

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): CalendarFragmentBinding {
        return CalendarFragmentBinding.inflate(layoutInflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        adapter = CalendarAdapter(requireContext())
        detailAdapter = CalendarDetailAdapter(requireContext())
        presenter = CalendarPresenter(
            this,
            adapter,
            adapter,
            detailAdapter,
            detailAdapter,
            DrinkRepository.apply {
                drinkLocalDataSource = DrinkLocalDataSource.apply {
                    drinkDao = DrinkDatabase.getInstance(requireContext()).drinkDao()
                }
            },
            SPUtils(requireContext())
        )

        initRecyclers()

        presenter.updateDate()
        presenter.updateProgress()
        presenter.updateDetails()

    }

    override fun updateCalendarDates(date: LocalDate) {
        binding.textCalendarDate.text = date.yearMonthKrFormat
        binding.textCalendarDetailDate.text = date.yearMonthDateKrFormat
    }

    override fun attachItemTouchHelper(itemTouchCallback: ItemTouchCallback) {
        val helper = ItemTouchHelper(itemTouchCallback)
        helper.attachToRecyclerView(binding.recyclerCalendarDetailDrinkList)
    }

    override fun showDialog(onPositive: ((Unit) -> Unit), onDismiss: ((Unit) -> Unit)) {
        AlertDialog.Builder(requireContext())
            .setTitle(resources.getString(R.string.calendar_delete_dialog_title))
            .setPositiveButton(
                resources.getString(R.string.calendar_delete_dialog_positive)
            ) { _, _ -> onPositive.invoke(Unit) }
            .setNegativeButton(
                resources.getString(R.string.calendar_delete_dialog_negative)
            ) { _, _ -> onDismiss.invoke(Unit) }
            .create()
            .show()
    }

    override fun changeProgressPercent(percent: Int) {
        binding.textGoalPercent.text = resources.getString(R.string.unit_percent, percent)
        binding.progressGoal.progress = percent
    }

    override fun changeDetailProgressPercent(percent: Int) {
        binding.progressDetailGoal.progress = percent
    }

    override fun changeDetailDrinkAmount(amount: Int) {
        if (amount == 0) {
            binding.textCalendarDetailDrinkAmount.text =
                resources.getString(R.string.calendar_detail_no_amount)
            changeDetailGroupVisibility(false)
        } else {
            binding.textCalendarDetailDrinkAmount.text =
                resources.getString(R.string.calendar_detail_amount, amount)
            changeDetailGroupVisibility(true)
        }
    }

    private fun changeDetailGroupVisibility(isVisible: Boolean) {
        if (isVisible) {
            binding.groupCalendarDetail.visibility = View.VISIBLE
        } else {
            binding.groupCalendarDetail.visibility = View.GONE
        }
    }

    private fun initRecyclers() {
        binding.recyclerCalendar.apply {
            adapter = this@CalendarFragment.adapter
            layoutManager = GridLayoutManager(requireContext(), 7)
        }

        binding.recyclerCalendarDetailDrinkList.apply {
            adapter = this@CalendarFragment.detailAdapter
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        }
        presenter.updateAdapterData()
        presenter.updateDetailAdapterData()
    }
}