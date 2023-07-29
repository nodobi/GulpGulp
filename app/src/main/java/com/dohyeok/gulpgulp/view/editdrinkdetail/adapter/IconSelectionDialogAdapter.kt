package com.dohyeok.gulpgulp.view.editdrinkdetail.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dohyeok.gulpgulp.databinding.EditdrinkIconselectionDialogItemBinding

class IconSelectionDialogAdapter(private val context: Context) :
    RecyclerView.Adapter<IconSelectionDialogViewHolder>(), IconSelectionDialogAdapterContract.View,
    IconSelectionDialogAdapterContract.Model {
//    override lateinit var iconIdData: MutableList<Int>
    override var iconIdData: MutableList<Int> = mutableListOf()
    override lateinit var onItemClick: (Int) -> Unit

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): IconSelectionDialogViewHolder {
        val holder = IconSelectionDialogViewHolder(
            EditdrinkIconselectionDialogItemBinding.inflate(
                LayoutInflater.from(context), parent, false
            )
        )
        holder.binding.editdrinkDialogItemImage.apply {
            setOnClickListener{
                onItemClick.invoke(holder.imageResId)
            }
        }
        return holder
    }

    override fun onBindViewHolder(holder: IconSelectionDialogViewHolder, position: Int) {
        holder.onBind(iconIdData[position])
    }

    override fun getItemCount(): Int {
        return iconIdData.size
    }
}