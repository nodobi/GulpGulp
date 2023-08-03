package com.dohyeok.gulpgulp.view.dialog.editdrink

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dohyeok.gulpgulp.data.Drink
import com.dohyeok.gulpgulp.data.DrinkRecord
import com.dohyeok.gulpgulp.databinding.EditdrinkBottomDialogBinding
import com.dohyeok.gulpgulp.view.dialog.editdrink.contract.EditDrinkBottomSheetDialogContract
import com.dohyeok.gulpgulp.view.dialog.editdrink.contract.EditDrinkBottomSheetDialogPresenter
import com.dohyeok.gulpgulp.view.dialog.iconseletion.IconSelectionDialogFragment
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.time.LocalTime

class EditDrinkBottomSheetDialogFragment : BottomSheetDialogFragment(),
    EditDrinkBottomSheetDialogContract.View {
    private var _binding: EditdrinkBottomDialogBinding? = null
    private val binding get() = _binding!!

    private lateinit var presenter: EditDrinkBottomSheetDialogPresenter
    private lateinit var iconSelectionDialog: IconSelectionDialogFragment
    private var targetDrink: Pair<Drink, Int>? = null
    private var isUseTimePicker = false

    override lateinit var onCommit: (Drink, LocalTime) -> Unit
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = EditdrinkBottomDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        presenter = EditDrinkBottomSheetDialogPresenter(this)

        if (targetDrink == null) {
            targetDrink = Pair(
                Drink("ic_bottle_24dp", "water", 250),
                resources.getIdentifier("ic_bottle_24dp", "drawable", requireContext().packageName)
            )
        }

        binding.apply {
            imageDrink.apply {
                setImageResource(targetDrink!!.second)
                tag = targetDrink!!.first.iconResName
                setOnClickListener {
                    presenter.onImageClick.invoke(it)
                }
            }
            edittextDrinkName.setText(targetDrink!!.first.name)
            edittextDrinkAmount.setText(targetDrink!!.first.amount.toString())
            timepickerDrinkTime.visibility = if(isUseTimePicker) {
                View.VISIBLE
            } else {
                View.GONE
            }
        }
        binding.btnCommit.setOnClickListener {
            onCommit.invoke(
                Drink(binding.imageDrink.tag.toString(),
                    binding.edittextDrinkName.text.toString(),
                    binding.edittextDrinkAmount.text.let {
                        if (it.toString() == "") return@let 0
                        else return@let it.toString().toInt()
                    }),
                LocalTime.of(binding.timepickerDrinkTime.hour, binding.timepickerDrinkTime.minute)
            )
            dismiss()
        }
        initIconSelectionDialog()
        presenter.onImageClick = { _ ->
            iconSelectionDialog.show(parentFragmentManager, "iconSelectionDialog")
        }
    }

    fun updateTargetDrinkData(drink: Drink, iconResId: Int?) {
        targetDrink = Pair(
            drink,
            iconResId ?: resources.getIdentifier(
                drink.iconResName,
                "drawable",
                requireContext().packageName
            )
        )
    }

    fun updateTargetDrinkData(drinkRecord: DrinkRecord, iconResId: Int?) {
        updateTargetDrinkData(drinkRecord.drink, iconResId)
    }

    fun useTimePicker(isUse: Boolean) {
        isUseTimePicker = isUse
    }

    private fun initIconSelectionDialog() {
        iconSelectionDialog = IconSelectionDialogFragment().apply {
            onIconClick = { resId, resName ->
                binding.imageDrink.setImageResource(resId)
                binding.imageDrink.tag = resName
                this.dismiss()
            }
        }
    }
}