package com.dohyeok.gulpgulp.view.calendar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.dohyeok.gulpgulp.databinding.CalendarFragmentBinding
import com.dohyeok.gulpgulp.databinding.CalendarIncludeDetailBinding
import com.dohyeok.gulpgulp.util.yearMonthDateKrFormat
import com.dohyeok.gulpgulp.util.yearMonthKrFormat
import com.dohyeok.gulpgulp.view.base.BaseFragment
import com.dohyeok.gulpgulp.view.calendar.adapter.CalendarAdapter
import com.dohyeok.gulpgulp.view.calendar.contract.CalendarContract
import com.dohyeok.gulpgulp.view.calendar.contract.CalendarPresenter
import java.time.LocalDate

class CalendarFragment : BaseFragment<CalendarFragmentBinding>(), CalendarContract.View {
    private val binding_include: CalendarIncludeDetailBinding get() = _binding_include!!
    private var _binding_include : CalendarIncludeDetailBinding? = null
    private lateinit var adapter: CalendarAdapter
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
        presenter = CalendarPresenter().apply {
            this.view = this@CalendarFragment
            adapterView = adapter
            adapterModel = adapter
        }

        binding.recyclerCalendar.apply {
            adapter = this@CalendarFragment.adapter
            layoutManager = GridLayoutManager(requireContext(), 7)
        }

        presenter.updateAdapterData()
        presenter.updateDate()

        binding.progressGoal.setProgress(50, false)
    }

    override fun updateCalendarDates(date: LocalDate) {
        binding.textCalendarDate.text = date.yearMonthKrFormat
        binding.textCalendarDetailDate.text = date.yearMonthDateKrFormat
    }
}