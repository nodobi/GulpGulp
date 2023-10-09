package com.dohyeok.gulpgulp.view.dialog.iconselection.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dohyeok.gulpgulp.data.icon.Icon
import com.dohyeok.gulpgulp.databinding.ItemIconselectionIconBinding

class IconListAdapter(private var context: Context) : RecyclerView.Adapter<IconListViewHolder>(),
    IconListAdapterContract.View, IconListAdapterContract.Model {
    override lateinit var onIconClick: (Icon) -> Unit
    private var iconData: MutableList<Icon> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IconListViewHolder {
        return IconListViewHolder(
            ItemIconselectionIconBinding.inflate(
                LayoutInflater.from(context),
                parent,
                false
            )
        ).apply {
            binding.root.setOnClickListener { onIconClick(icon) }
        }
    }

    override fun onBindViewHolder(holder: IconListViewHolder, position: Int) {
        holder.onBind(iconData[position])
    }

    override fun getItemCount(): Int {
        return iconData.size
    }

    override fun notifyIconsChange() {
        notifyDataSetChanged()
    }

    override fun loadIconData(iconList: List<Icon>) {
        iconData.clear()
        iconData.addAll(iconList)
    }
}