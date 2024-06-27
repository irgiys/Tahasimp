package com.irgiys.tahasimp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.irgiys.tahasimp.databinding.TransactionItemBinding
import com.irgiys.tahasimp.db.entity.HistoryTransactionEntity
import com.irgiys.tahasimp.utils.HistoryTransactionDiffCallback
import com.irgiys.tahasimp.utils.formatCurrency
import com.irgiys.tahasimp.utils.formatDate

class HistoryListAdapter :
    ListAdapter<HistoryTransactionEntity, HistoryListAdapter.HistoryTransactionViewHolder>(
        HistoryTransactionDiffCallback()
    ) {

    inner class HistoryTransactionViewHolder(private val binding: TransactionItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(transaction: HistoryTransactionEntity) {
            val isSaving = transaction.type == "saving"
            val setColor = if (isSaving) "#4CAF50" else "#F44336"
            binding.apply {
                tvDateCreated.text = formatDate(transaction.date)// Format date as needed
                tvAmount.text =
                    if (isSaving) formatCurrency(transaction.amount) else "-" + formatCurrency(
                        transaction.amount
                    )
                tvAmount.setTextColor(android.graphics.Color.parseColor(setColor))
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HistoryTransactionViewHolder {
        val binding =
            TransactionItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HistoryTransactionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HistoryTransactionViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}
