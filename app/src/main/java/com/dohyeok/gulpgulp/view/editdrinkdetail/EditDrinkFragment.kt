package com.dohyeok.gulpgulp.view.editdrinkdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.dohyeok.gulpgulp.data.source.drink.DrinkRepository
import com.dohyeok.gulpgulp.data.source.drink.local.DrinkDatabase
import com.dohyeok.gulpgulp.data.source.drink.local.DrinkLocalDataSource
import com.dohyeok.gulpgulp.databinding.EditdrinkFragmentBinding
import com.dohyeok.gulpgulp.view.base.BaseFragment
import com.dohyeok.gulpgulp.view.dialog.editdrink.EditDrinkBottomSheetDialogFragment
import com.dohyeok.gulpgulp.view.editdrinkdetail.adapter.ExistDrinkAdapter
import com.dohyeok.gulpgulp.view.editdrinkdetail.contract.EditDrinkContract
import com.dohyeok.gulpgulp.view.editdrinkdetail.contract.EditDrinkPresenter

class EditDrinkFragment : BaseFragment<EditdrinkFragmentBinding>(), EditDrinkContract.View {
    override lateinit var presenter: EditDrinkPresenter
    private lateinit var existAdapter: ExistDrinkAdapter
    private lateinit var bottomSheetDialog: EditDrinkBottomSheetDialogFragment
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): EditdrinkFragmentBinding {
        return EditdrinkFragmentBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        existAdapter = ExistDrinkAdapter(requireContext())
        presenter = EditDrinkPresenter(this, existAdapter, existAdapter, DrinkRepository.apply {
            drinkLocalDataSource = DrinkLocalDataSource.apply {
                drinkDao = DrinkDatabase.getInstance(requireContext()).drinkDao()
            }
        })

        presenter.updateDrinkData()

        binding.recyclerExistDrink.apply {
            this.adapter = existAdapter
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        }

        initBottomSheetDialog()

        binding.btnAddDrink.setOnClickListener {
            bottomSheetDialog.show(parentFragmentManager, "dialog")
        }

    }

    private fun initBottomSheetDialog() {
        bottomSheetDialog = EditDrinkBottomSheetDialogFragment().apply {
            setOnCommitBtnClickListener { editedDrink ->
                this@EditDrinkFragment.presenter.onDrinkAddBtnClick.invoke(
                    editedDrink
                )
                this.dismiss()
            }
        }

    }

}