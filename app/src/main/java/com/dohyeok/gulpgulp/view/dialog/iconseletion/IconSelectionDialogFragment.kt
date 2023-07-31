package com.dohyeok.gulpgulp.view.dialog.iconseletion

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.GridLayoutManager
import com.dohyeok.gulpgulp.databinding.EditdrinkIconselectionDialogBinding
import com.dohyeok.gulpgulp.view.dialog.iconseletion.adapter.IconSelectionDialogAdapter
import com.dohyeok.gulpgulp.view.dialog.iconseletion.contract.IconSelectionDialogContract
import com.dohyeok.gulpgulp.view.dialog.iconseletion.contract.IconSelectionDialogPresenter

class IconSelectionDialogFragment : DialogFragment(), IconSelectionDialogContract.View {
    private lateinit var presenter: IconSelectionDialogContract.Presenter
    private lateinit var iconSelectionDialogAdapter: IconSelectionDialogAdapter
    private var _binding: EditdrinkIconselectionDialogBinding? = null
    private val binding get() = _binding!!

    lateinit var onIconClick: (Int, String) -> Unit

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = EditdrinkIconselectionDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        iconSelectionDialogAdapter = IconSelectionDialogAdapter(requireContext()).apply {
            onItemClick = onIconClick
        }
        presenter = IconSelectionDialogPresenter(
            this,
            iconSelectionDialogAdapter,
            iconSelectionDialogAdapter
        )

        binding.editdrinkDialogRecyclerview.apply {
            adapter = iconSelectionDialogAdapter
            layoutManager = GridLayoutManager(requireContext(), 3)
        }

        presenter.initIconIdData()
    }
}