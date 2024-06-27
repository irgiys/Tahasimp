package com.irgiys.tahasimp.ui.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.irgiys.tahasimp.databinding.SavingItemBinding
import com.irgiys.tahasimp.db.entity.SavingEntity
import com.irgiys.tahasimp.ui.activity.DetailSavingActivity
import com.irgiys.tahasimp.utils.SavingDiffCallback
import com.irgiys.tahasimp.utils.formatCurrency

class SavingListAdapter : ListAdapter<SavingEntity, SavingListAdapter.SavingViewHolder>(SavingDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SavingViewHolder {
        val binding = SavingItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SavingViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SavingViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class SavingViewHolder(private val binding: SavingItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(saving: SavingEntity) {
            with(binding) {
                tvTitle.text = saving.title
                tvTargetSaving.text = formatCurrency(saving.target)
                tvDailySaving.text = "${formatCurrency(saving.dailyTarget)} per hari"
                tvDayTarget.text = "Estimasi ${saving.dayTarget.toString()} hari"
                cvItemSaving.setOnClickListener {
                    val intent = Intent(it.context, DetailSavingActivity::class.java)
                    intent.putExtra(DetailSavingActivity.EXTRA_SAVING, saving)
                    it.context.startActivity(intent)
                }
            }
        }
    }
}
