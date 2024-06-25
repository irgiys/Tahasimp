package com.irgiys.tahasimp.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.irgiys.tahasimp.databinding.SavingItemBinding
import com.irgiys.tahasimp.db.entity.SavingEntity
import com.irgiys.tahasimp.utils.SavingDiffCallback
import com.irgiys.tahasimp.utils.formatCurrency


class SavingListAdapter : RecyclerView.Adapter<SavingListAdapter.SavingViewHolder>() {
    private val listSaving = ArrayList<SavingEntity>()
    fun setListSaving(listSaving: List<SavingEntity>) {
        val diffCallback = SavingDiffCallback(
            this.listSaving,
            listSaving
        )
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.listSaving.clear()
        this.listSaving.addAll(listSaving)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType:
        Int
    ): SavingViewHolder {
        val binding =
            SavingItemBinding.inflate(
                LayoutInflater.from(parent.context), parent,
                false
            )
        return SavingViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: SavingViewHolder,
        position: Int
    ) {
        holder.bind(listSaving[position])
    }

    override fun getItemCount(): Int {
        return listSaving.size
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
//                tvItemDescription.text = saving.description
//                cvItemNote.setOnClickListener {
//                    val intent = Intent(it.context, NoteAddUpdateActivity::class.java
//                    )
//                    intent.putExtra(NoteAddUpdateActivity.EXTRA_NOTE, note)
//                    it.context.startActivity(intent)
//                }
            }
        }
    }
}