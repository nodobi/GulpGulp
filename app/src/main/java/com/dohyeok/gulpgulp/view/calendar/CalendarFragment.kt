package com.dohyeok.gulpgulp.view.calendar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.dohyeok.gulpgulp.data.source.drink.DrinkRepository
import com.dohyeok.gulpgulp.data.source.drink.local.DrinkDatabase
import com.dohyeok.gulpgulp.data.source.drink.local.DrinkLocalDataSource
import com.dohyeok.gulpgulp.databinding.CalendarFragmentBinding
import com.dohyeok.gulpgulp.databinding.CalendarIncludeDetailBinding
import com.dohyeok.gulpgulp.util.yearMonthDateKrFormat
import com.dohyeok.gulpgulp.util.yearMonthKrFormat
import com.dohyeok.gulpgulp.view.base.BaseFragment
import com.dohyeok.gulpgulp.view.calendar.adapter.CalendarAdapter
import com.dohyeok.gulpgulp.view.calendar.adapter.CalendarDetailAdapter
import com.dohyeok.gulpgulp.view.calendar.contract.CalendarContract
import com.dohyeok.gulpgulp.view.calendar.contract.CalendarPresenter
import java.time.LocalDate

class CalendarFragment : BaseFragment<CalendarFragmentBinding>(), CalendarContract.View {
    private val binding_include: CalendarIncludeDetailBinding get() = _binding_include!!
    private var _binding_include: CalendarIncludeDetailBinding? = null
    private lateinit var adapter: CalendarAdapter
    private lateinit var detailAdapter: CalendarDetailAdapter
    lateinit var presenter: CalendarPresenter

    override fun onDestroyView() {
        super.onDestroyView()
        _binding_include = null
    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): CalendarFragmentBinding {
        return CalendarFragmentBinding.inflate(layoutInflater, container, false).also {
            _binding_include = CalendarIncludeDetailBinding.bind(it.root)
        }
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
            })
        presenter.updateDetailData()

        binding.recyclerCalendar.apply {
            adapter = this@CalendarFragment.adapter
            layoutManager = GridLayoutManager(requireContext(), 7)
        }

        binding_include.recyclerCalendarDate.apply {
            adapter = this@CalendarFragment.detailAdapter
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        }

        presenter.updateAdapterData()
        presenter.updateDate()

        binding.progressGoal.setProgress(50, false)
    }

    override fun updateCalendarDates(date: LocalDate) {
        binding.textCalendarDate.text = date.yearMonthKrFormat
        binding.textCalendarDetailDate.text = date.yearMonthDateKrFormat
    }

    override fun attachItemTouchHelper(itemTouchCallback: ItemTouchCallback) {
        val helper = ItemTouchHelper(itemTouchCallback)
        helper.attachToRecyclerView(binding_include.recyclerCalendarDate)
    }

    override fun showDialog(onPositive: ((Unit) -> Unit), onDismiss: ((Unit) -> Unit)) {
        AlertDialog.Builder(requireContext())
            .setTitle("정말 지우시겠습니까 ?")
            .setPositiveButton(
                "ok"
            ) { dialog, which -> onPositive.invoke(Unit) }
            .setNegativeButton(
                "cancel"
            ) { dialog, which -> onDismiss.invoke(Unit) }
            .create()
            .show()
    }
}