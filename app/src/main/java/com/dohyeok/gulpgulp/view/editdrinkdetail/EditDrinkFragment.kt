package com.dohyeok.gulpgulp.view.editdrinkdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.dohyeok.gulpgulp.R
import com.dohyeok.gulpgulp.data.Drink
import com.dohyeok.gulpgulp.data.source.drink.DrinkRepository
import com.dohyeok.gulpgulp.data.source.drink.local.DrinkDatabase
import com.dohyeok.gulpgulp.data.source.drink.local.DrinkLocalDataSource
import com.dohyeok.gulpgulp.databinding.EditdrinkBottomDialogBinding
import com.dohyeok.gulpgulp.databinding.EditdrinkFragmentBinding
import com.dohyeok.gulpgulp.view.base.BaseFragment
import com.dohyeok.gulpgulp.view.editdrinkdetail.adapter.ExistDrinkAdapter
import com.dohyeok.gulpgulp.view.editdrinkdetail.contract.EditDrinkContract
import com.dohyeok.gulpgulp.view.editdrinkdetail.contract.EditDrinkPresenter
import com.google.android.material.bottomsheet.BottomSheetDialog

class EditDrinkFragment : BaseFragment<EditdrinkFragmentBinding>(), EditDrinkContract.View {
    override lateinit var presenter: EditDrinkPresenter
    private lateinit var existAdapter: ExistDrinkAdapter
    private lateinit var bottomSheetBinding: EditdrinkBottomDialogBinding
    private lateinit var bottomSheetDialog: BottomSheetDialog
    private lateinit var iconSelectionDialog: IconSelectionDialogFragment
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

        bottomSheetDialog = initBottomSheetDialog()
        initIconSelectionDialog()

        binding.btnAddDrink.setOnClickListener {
            bottomSheetDialog.show()
        }

    }

    private fun initBottomSheetDialog(): BottomSheetDialog {
        bottomSheetBinding =
            EditdrinkBottomDialogBinding.inflate(layoutInflater, binding.root, false)
        bottomSheetBinding.imageDrink.tag = R.drawable.ic_bottle_24dp

        bottomSheetBinding.imageDrink.setOnClickListener {
            iconSelectionDialog.show(parentFragmentManager, "dialog")
        }

        bottomSheetBinding.btnCommit.setOnClickListener {
            presenter.onDrinkAddBtnClick.invoke(
                Drink(
                    bottomSheetBinding.imageDrink.tag as Int,
                    bottomSheetBinding.edittextDrinkName.text.toString(),
                    bottomSheetBinding.edittextDrinkAmount.text.let {
                        if(it.toString() == "") return@let 0
                        else return@let it.toString().toInt()
                    }
                )
            )
            bottomSheetDialog.dismiss()
        }
        return BottomSheetDialog(requireContext()).apply {
            setContentView(bottomSheetBinding.root)
        }
    }

    override fun updateBottomSheetIcon(resId: Int) {
        bottomSheetBinding.imageDrink.apply {
            setImageResource(resId)
            tag = resId
        }
    }

    private fun initIconSelectionDialog() {
        iconSelectionDialog = IconSelectionDialogFragment().apply {
            onIconClick = {
                this@EditDrinkFragment.presenter.onIconSelected(it)
                dismiss()
            }

        }
    }
}