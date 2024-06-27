package com.irgiys.tahasimp.utils

import androidx.recyclerview.widget.DiffUtil
import com.irgiys.tahasimp.db.entity.SavingEntity

class SavingDiffCallback : DiffUtil.ItemCallback<SavingEntity>() {
    override fun areItemsTheSame(oldItem: SavingEntity, newItem: SavingEntity): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: SavingEntity, newItem: SavingEntity): Boolean {
        return oldItem == newItem
    }
}
