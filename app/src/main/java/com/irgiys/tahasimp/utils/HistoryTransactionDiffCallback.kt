package com.irgiys.tahasimp.utils

import androidx.recyclerview.widget.DiffUtil
import com.irgiys.tahasimp.db.entity.HistoryTransactionEntity

class HistoryTransactionDiffCallback : DiffUtil.ItemCallback<HistoryTransactionEntity>() {
    override fun areItemsTheSame(
        oldItem: HistoryTransactionEntity,
        newItem: HistoryTransactionEntity
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: HistoryTransactionEntity,
        newItem: HistoryTransactionEntity
    ): Boolean {
        return oldItem == newItem
    }
}
