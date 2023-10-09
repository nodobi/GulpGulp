package com.dohyeok.gulpgulp.view.managedrink

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.dohyeok.gulpgulp.data.drink.DrinkItem
import com.dohyeok.gulpgulp.data.drink.DrinkRepository
import com.dohyeok.gulpgulp.databinding.FragmentManagedrinkBinding
import com.dohyeok.gulpgulp.view.base.BaseFragment
import com.dohyeok.gulpgulp.view.dialog.addeditdrink.AddEditDrinkDialogFragment
import com.dohyeok.gulpgulp.view.managedrink.adapter.ManageDrinkAdapter
import com.dohyeok.gulpgulp.view.managedrink.adapter.ManageDrinkAdapterItemTouchCallback
import com.dohyeok.gulpgulp.view.managedrink.contract.ManageDrinkContract
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ManageDrinkFragment : BaseFragment<FragmentManagedrinkBinding>(), ManageDrinkContract.View {
    @Inject
    lateinit var presenter: ManageDrinkContract.Presenter

    // 아래와 같이 사용하면,, drinkAdapter 와 presenter 안의 adapter 가 서로 다르다
//    @Inject
//    lateinit var drinkAdapter: ManageDrinkAdapter

    @Inject
    lateinit var drinkRepository: DrinkRepository
    override fun initFragmentViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentManagedrinkBinding {
        return FragmentManagedrinkBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        presenter.attachView(this)

        presenter.adapterModel.apply {
            onDrinkLongClick = {
                showEditDrinkDialog(it)
            }
        }


        binding.recyclerDrinkList.apply {
            adapter = presenter.adapterModel as ManageDrinkAdapter
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        }
        binding.btnAddDrink.apply {
            setOnClickListener {
                showAddDrinkDialog()
            }
        }

        presenter.loadDrinkItemData(requireContext())
        attachItemTouchHelper()
    }

    private fun showAddDrinkDialog() {
        AddEditDrinkDialogFragment().apply {
            onCommit = { drink, _ ->
                this@ManageDrinkFragment.presenter.onAddDrinkDialogCommit.invoke(
                    drink,
                    this@ManageDrinkFragment.requireContext()
                )
                dismiss()
            }
        }.show(childFragmentManager, "AddDrinkDialog")
    }

    private fun showEditDrinkDialog(target: DrinkItem) {
        AddEditDrinkDialogFragment().apply {
            onCommit = { drink, _ ->
                this@ManageDrinkFragment.presenter.onEditDrinkDialogCommit.invoke(
                    drink,
                    this@ManageDrinkFragment.requireContext()
                )
                dismiss()
            }
            setTargetDrink(target)
        }.show(childFragmentManager, "AddDrinkDialog")
    }

    private fun attachItemTouchHelper() {
        val itemMoveListener = ItemTouchHelper(
            ManageDrinkAdapterItemTouchCallback(
                onItemMove = { holder, target -> presenter.onItemMove(holder, target) },
                onItemSwiped = { holder, direction -> presenter.onItemSwiped(holder, direction) },
                onItemSelectedChanged = { holder, actionState ->
                    presenter.onItemSelectedChanged(
                        holder,
                        actionState
                    )
                }
            )
        )
        itemMoveListener.attachToRecyclerView(binding.recyclerDrinkList)
    }
}