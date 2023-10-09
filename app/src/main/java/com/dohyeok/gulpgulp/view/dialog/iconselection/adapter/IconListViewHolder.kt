package com.dohyeok.gulpgulp.view.dialog.iconselection.adapter

import androidx.recyclerview.widget.RecyclerView
import com.dohyeok.gulpgulp.data.icon.Icon
import com.dohyeok.gulpgulp.databinding.ItemIconselectionIconBinding

class IconListViewHolder(var binding: ItemIconselectionIconBinding): RecyclerView.ViewHolder(binding.root) {
    lateinit var icon: Icon

    fun onBind(icon: Icon) {
        this.icon = icon
        binding.imgIcon.setImageResource(icon.resId)
    }
}