package com.dohyeok.gulpgulp.view.editdrinkdetail.adapter

import androidx.recyclerview.widget.RecyclerView
import com.dohyeok.gulpgulp.databinding.EditdrinkIconselectionDialogItemBinding

class IconSelectionDialogViewHolder(val binding: EditdrinkIconselectionDialogItemBinding): RecyclerView.ViewHolder(binding.root) {
    var imageResId: Int = -1

    fun onBind(resId: Int) {
        binding.editdrinkDialogItemImage.setImageResource(resId)
        imageResId = resId
    }
}
