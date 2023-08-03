package com.dohyeok.gulpgulp.view.calendar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.dohyeok.gulpgulp.R
import com.dohyeok.gulpgulp.data.DrinkRecord
import com.dohyeok.gulpgulp.data.source.drink.DrinkRepository
import com.dohyeok.gulpgulp.data.source.drink.local.DrinkDatabase
import com.dohyeok.gulpgulp.data.source.drink.local.DrinkLocalDataSource
import com.dohyeok.gulpgulp.databinding.CalendarFragmentBinding
import com.dohyeok.gulpgulp.util.SPUtils
import com.dohyeok.gulpgulp.util.yearMonthDateKrFormat
import com.dohyeok.gulpgulp.util.yearMonthKrFormat
import com.dohyeok.gulpgulp.view.base.BaseFragment
import com.dohyeok.gulpgulp.view.calendar.adapter.CalendarDayBinder
import com.dohyeok.gulpgulp.view.calendar.adapter.CalendarDetailAdapter
import com.dohyeok.gulpgulp.view.calendar.adapter.CalendarHeaderBinder
import com.dohyeok.gulpgulp.view.calendar.contract.CalendarContract
import com.dohyeok.gulpgulp.view.calendar.contract.CalendarPresenter
import com.dohyeok.gulpgulp.view.dialog.editdrink.EditDrinkBottomSheetDialogFragment
import com.kizitonwose.calendar.core.daysOfWeek
import java.time.LocalDate
import java.time.YearMonth

class CalendarFragment : BaseFragment<CalendarFragmentBinding>(), CalendarContract.View {
    private lateinit var dayBinder: CalendarDayBinder
    private lateinit var headerBinder: CalendarHeaderBinder
    private lateinit var detailAdapter: CalendarDetailAdapter
    lateinit var presenter: CalendarContract.Presenter

    override fun getFragmentBinding(
        inflater: LayoutInflater, container: ViewGroup?
    ): CalendarFragmentBinding {
        return CalendarFragmentBinding.inflate(layoutInflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        dayBinder = CalendarDayBinder()
        headerBinder = CalendarHeaderBinder()
        detailAdapter = CalendarDetailAdapter(requireContext())
        presenter = CalendarPresenter(
            this, dayBinder, headerBinder, detailAdapter, detailAdapter, DrinkRepository.apply {
                drinkLocalDataSource = DrinkLocalDataSource.apply {
                    drinkDao = DrinkDatabase.getInstance(requireContext()).drinkDao()
                }
            }, SPUtils(requireContext())
        )

        initCalendar()
        initRecycler()

        presenter.updateDate()
        presenter.updateProgress()
        presenter.updateDetails()

        binding.btnAddMissingDrinks.setOnClickListener {
            showAddDrinkRecordDialog()
        }
    }


    override fun updateCalendarDate(date: LocalDate) {
        binding.textCalendarDate.text = date.yearMonthKrFormat
    }

    override fun updateCalendarDetailDate(date: LocalDate) {
        binding.textCalendarDetailDate.text = date.yearMonthDateKrFormat
    }

    override fun attachItemTouchHelper(itemTouchCallback: ItemTouchCallback) {
        val helper = ItemTouchHelper(itemTouchCallback)
        helper.attachToRecyclerView(binding.recyclerCalendarDetailDrinkList)
    }

    override fun showDialog(onPositive: ((Unit) -> Unit), onNegative: ((Unit) -> Unit)) {
        AlertDialog.Builder(requireContext())
            .setTitle(resources.getString(R.string.calendar_delete_dialog_title)).setPositiveButton(
                resources.getString(R.string.calendar_delete_dialog_positive)
            ) { _, _ -> onPositive.invoke(Unit) }.setNegativeButton(
                resources.getString(R.string.calendar_delete_dialog_negative)
            ) { _, _ -> onNegative.invoke(Unit) }.create().show()
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

    override fun setCalendarEvents(onPrev: () -> Unit, onNext: () -> Unit) {
        binding.imageCalendarPrev.setOnClickListener {
            onPrev()
        }
        binding.imageCalendarNext.setOnClickListener {
            onNext()
        }
    }

    override fun moveCalendar(date: LocalDate) {
        binding.calendar.smoothScrollToDate(date)
        updateCalendarDate(date)
    }

    override fun notifyCalendarDateChanged(date: LocalDate) {
        binding.calendar.notifyDateChanged(date)
    }

    override fun notifyCalendarMonthChanged(yearMonth: YearMonth) {
        binding.calendar.notifyMonthChanged(yearMonth)
    }

    override fun showEditDrinkRecordDialog(drinkRecord: DrinkRecord, iconResId: Int) {
        EditDrinkBottomSheetDialogFragment().apply {
            updateTargetDrinkData(drinkRecord, iconResId)
            onCommit = {editedDrink, _ ->
                presenter.onDrinkRecordEdit.invoke(drinkRecord, editedDrink)
            }
        }.show(parentFragmentManager, "editDrinkDialog")
    }

    override fun showAddDrinkRecordDialog() {
        EditDrinkBottomSheetDialogFragment().apply {
            onCommit = { newDrink, addTime ->
                presenter.onAddMissingDrinkRecordBtnClick.invoke(newDrink)
            }
            useTimePicker(true)
        }.show(parentFragmentManager, "AddMissingDrinkDialog")
    }

    private fun changeDetailGroupVisibility(isVisible: Boolean) {
        if (isVisible) {
            binding.groupCalendarDetail.visibility = View.VISIBLE
        } else {
            binding.groupCalendarDetail.visibility = View.GONE
        }
    }

    private fun initRecycler() {
        binding.recyclerCalendarDetailDrinkList.apply {
            adapter = this@CalendarFragment.detailAdapter
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        }

        presenter.updateDetailAdapterData()
        presenter.setAdapterEvents()
    }


    private fun initCalendar() {
        binding.calendar.dayBinder = dayBinder
        binding.calendar.monthHeaderBinder = headerBinder

        val daysOfWeek = daysOfWeek()
        val currentMonth = YearMonth.now()
        val startMonth = currentMonth.minusMonths(200)
        val endMonth = currentMonth.plusMonths(200)

        binding.calendar.apply {
            setup(startMonth, endMonth, daysOfWeek.first())
            scrollToMonth(currentMonth)
            monthScrollListener = { month ->
                presenter.onCalendarScroll(month)
            }
        }
    }


}