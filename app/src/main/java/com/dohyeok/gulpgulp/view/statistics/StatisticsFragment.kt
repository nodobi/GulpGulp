package com.dohyeok.gulpgulp.view.statistics

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.dohyeok.gulpgulp.R
import com.dohyeok.gulpgulp.data.drink.DrinkItem
import com.dohyeok.gulpgulp.data.record.DrinkRecord
import com.dohyeok.gulpgulp.databinding.FragmentStatisticsBinding
import com.dohyeok.gulpgulp.view.base.BaseFragment
import com.dohyeok.gulpgulp.view.dialog.addeditdrink.AddEditDrinkDialogFragment
import com.dohyeok.gulpgulp.view.statistics.adapter.CalendarDayBinder
import com.dohyeok.gulpgulp.view.statistics.adapter.RecordListAdapter
import com.dohyeok.gulpgulp.view.statistics.adapter.RecordListAdapterItemTouchCallback
import com.dohyeok.gulpgulp.view.statistics.contract.StatisticsContract
import com.kizitonwose.calendar.core.DayPosition
import com.kizitonwose.calendar.core.daysOfWeek
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDate
import java.time.YearMonth
import javax.inject.Inject

// TODO("아이템 longclick 시 수정할 수 있게 변경")
@AndroidEntryPoint
class StatisticsFragment : BaseFragment<FragmentStatisticsBinding>(),
    StatisticsContract.View {
    @Inject
    lateinit var presenter: StatisticsContract.Presenter

    override fun initFragmentViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentStatisticsBinding {
        val tmp = FragmentStatisticsBinding.inflate(inflater, container, false)
        return tmp
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        presenter.attachView(this)

        binding.btnAddMissing.setOnClickListener {
            showAddRecordDialog()
        }

        ItemTouchHelper(
            RecordListAdapterItemTouchCallback { holder, position ->
                presenter.onItemSwiped(holder, position)
            }
        ).attachToRecyclerView(binding.recyclerDateRecords)


        initCalendar()
        initRecycler()

        presenter.loadData()
    }

    @SuppressLint("StringFormatMatches")
    override fun updateCalendarHeader(yearMonth: YearMonth) {
        yearMonth.let {
            binding.textCalendarDate.text = requireContext().resources.getString(
                R.string.date_format_yearMonth,
                it.year,
                it.monthValue
            )
        }

    }

    override fun notifyCalendarDateChanged(date: LocalDate) {
        binding.calendar.notifyDateChanged(
            date,
            DayPosition.InDate,
            DayPosition.MonthDate,
            DayPosition.OutDate
        )
    }

    override fun updateRecordHeader(date: LocalDate) {
        binding.textDateRecords.text = resources.getString(
            R.string.date_format_yearMonthDate,
            date.year,
            date.monthValue,
            date.dayOfMonth
        )
    }

    override fun showEditRecordDialog(record: DrinkRecord) {
        AddEditDrinkDialogFragment().apply {
            onCommit = { drink, time ->
                this@StatisticsFragment.presenter.onCommitEditDialog(record.apply {
                    this.drink = drink
                    this.recordTime = time
                })
                dismiss()
            }
//            initEditedDrinkItem()
            isEditRecord = true
        }.show(childFragmentManager, "changeRecordDialog")
    }
    override fun showAddRecordDialog() {
        AddEditDrinkDialogFragment().apply {
            onCommit = { drink, time ->
                this@StatisticsFragment.presenter.onCommitAddDialog(drink, time)
                dismiss()
            }
            isEditRecord = true
        }.show(childFragmentManager, "changeRecordDialog")

    }

    private fun initCalendar() {
        val daysOfWeek = daysOfWeek()
        val currentMonth = YearMonth.now()
        val startMonth = currentMonth.minusMonths(200)
        val endMonth = currentMonth.plusMonths(200)

        binding.calendar.apply {
            dayBinder = presenter.calendarDayBinder as CalendarDayBinder
            setup(startMonth, endMonth, daysOfWeek.first())
            scrollToMonth(currentMonth)
            monthScrollListener = {
                presenter.onScrollCalendar(it)
            }
        }
    }

    private fun initRecycler() {
        binding.recyclerDateRecords.apply {
            adapter = presenter.recordListAdapterModel as RecordListAdapter
            layoutManager = LinearLayoutManager(
                this@StatisticsFragment.requireContext(),
                LinearLayoutManager.VERTICAL,
                false
            )
        }
    }
}
