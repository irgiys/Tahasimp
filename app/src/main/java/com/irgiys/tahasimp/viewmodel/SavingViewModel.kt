package com.irgiys.tahasimp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.irgiys.tahasimp.db.entity.SavingEntity
import com.irgiys.tahasimp.db.entity.SavingTransactionEntity
import com.irgiys.tahasimp.db.room.SavingDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class SavingViewModel(private val dao: SavingDao) : ViewModel() {

    fun insertSaving(saving: SavingEntity) {
        viewModelScope.launch {
            dao.insertSaving(saving)
        }
    }

    fun getAllSavings(): Flow<List<SavingEntity>> {
        return dao.getAllSavings()
    }

    fun insertTransaction(transaction: SavingTransactionEntity) {
        viewModelScope.launch {
            dao.insertTransaction(transaction)
        }
    }

    fun getTransactionsForSaving(savingId: Int): Flow<List<SavingTransactionEntity>> {
        return dao.getTransactionsForSaving(savingId)
    }
}
