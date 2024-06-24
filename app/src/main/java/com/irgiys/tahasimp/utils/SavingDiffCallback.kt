package com.irgiys.tahasimp.utils

import androidx.recyclerview.widget.DiffUtil
import com.irgiys.tahasimp.db.entity.SavingEntity

class SavingDiffCallback(
    private val oldSavingList: List<SavingEntity>,
    private val newSavingList: List<SavingEntity>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldSavingList.size
    override fun getNewListSize(): Int = newSavingList.size
    override fun areItemsTheSame(
        oldItemPosition: Int,
        newItemPosition: Int
    ): Boolean {
        return oldSavingList[oldItemPosition].id == newSavingList[newItemPosition].id
    }

    override fun areContentsTheSame(
        oldItemPosition: Int,
        newItemPosition: Int
    ): Boolean {
        val oldSaving = oldSavingList[oldItemPosition]
        val newSaving = newSavingList[newItemPosition]
        return oldSaving.title == newSaving.title &&
                oldSaving.dayTarget == newSaving.dayTarget &&
                oldSaving.target == newSaving.target &&
                oldSaving.dateCreated == newSaving.dateCreated

    }
}