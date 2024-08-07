package com.irgiys.tahasimp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.irgiys.tahasimp.db.entity.HistoryTransactionEntity
import com.irgiys.tahasimp.db.entity.SavingEntity
import kotlinx.coroutines.launch
import com.irgiys.tahasimp.repository.SavingRepository


class SavingViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: SavingRepository = SavingRepository(application)

    private val _netSavings = MutableLiveData<Long>()
    val netSavings: LiveData<Long> get() = _netSavings

    private val _historyTransactions = MutableLiveData<List<HistoryTransactionEntity>>()
    val historyTransactions: LiveData<List<HistoryTransactionEntity>> get() = _historyTransactions

    val allProgressSavings: LiveData<List<SavingEntity>> = repository.getAllProgressSavings().asLiveData()
    val allCompletedSavings: LiveData<List<SavingEntity>> = repository.getAllCompletedSavings().asLiveData()

    fun insertSaving(savingEntity: SavingEntity) {
        viewModelScope.launch {
            repository.insertSaving(savingEntity)
        }
    }

    fun insertTransaction(transactionEntity: HistoryTransactionEntity) {
        viewModelScope.launch {
            repository.insertTransaction(transactionEntity)
        }
    }

    fun loadNetSavings(savingId: Int) {
        viewModelScope.launch {
            repository.getNetSavings(savingId).collect {
                _netSavings.value = it
            }
        }
    }

    fun loadHistoryTransactions(savingId: Int) {
        viewModelScope.launch {
            repository.getHistoryTransactions(savingId).collect {
                _historyTransactions.value = it
            }
        }
    }

    fun updateSaving(savingEntity: SavingEntity) {
        viewModelScope.launch {
            repository.updateSaving(savingEntity)
        }
    }

    fun deleteSaving(savingEntity: SavingEntity) {
        viewModelScope.launch {
            repository.deleteSaving(savingEntity)
        }
    }


}
