package com.dohyeok.gulpgulp.view.dialog.iconselection

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.GridLayoutManager
import com.dohyeok.gulpgulp.data.icon.Icon
import com.dohyeok.gulpgulp.databinding.DialogIconselectionBinding
import com.dohyeok.gulpgulp.view.dialog.iconselection.adapter.IconListAdapter
import com.dohyeok.gulpgulp.view.dialog.iconselection.contract.IconSelectionDialogContract
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class IconSelectionDialogFragment : DialogFragment(), IconSelectionDialogContract.View {
    private var _binding: DialogIconselectionBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var presenter: IconSelectionDialogContract.Presenter

    override lateinit var onCommit: (Icon?) -> Unit
    override lateinit var onDismiss: (Unit) -> Unit

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = DialogIconselectionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        presenter.attachView(this)
        binding.apply {
            btnCommit.setOnClickListener { presenter.onCommit(Unit) }
            btnDismiss.setOnClickListener { presenter.onDismiss(Unit) }
            recyclerIconList.apply {
                adapter = presenter.iconListAdapterModel as IconListAdapter
                layoutManager = GridLayoutManager(requireContext(), 3)
            }
        }

        presenter.loadIcons()
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        _binding = null
        presenter.detachView()
        super.onDestroyView()
    }
}
