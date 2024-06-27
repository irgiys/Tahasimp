package com.irgiys.tahasimp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.irgiys.tahasimp.db.entity.HistoryTransactionEntity
import com.irgiys.tahasimp.db.entity.SavingEntity

import com.irgiys.tahasimp.repository.SavingRepository
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch


class SavingViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: SavingRepository = SavingRepository(application)

    private val _netSavings = MutableLiveData<Long>()
    val netSavings: LiveData<Long> get() = _netSavings

    private val _historyTransactions = MutableLiveData<List<HistoryTransactionEntity>>()
    val historyTransactions: LiveData<List<HistoryTransactionEntity>> get() = _historyTransactions

    // Mengambil semua tabungan
    val allSavings: LiveData<List<SavingEntity>> = repository.getAllSavings().asLiveData()

    // Menyimpan tabungan baru
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

    // Mengambil semua transaksi tabungan
//    fun getTransactionsForSaving(savingId: Int): LiveData<List<SavingTransactionEntity>> {
//        return repository.getTransactionsForSaving(savingId).asLiveData()
//    }



    // Mengambil semua penarikan tabungan
//    fun getWithdrawalsForSaving(savingId: Int): LiveData<List<WithdrawalTransactionEntity>> {
//        return repository.getWithdrawalsForSaving(savingId).asLiveData()
//    }

    // Mendapatkan total dana yang terkumpul untuk tabungan tertentu
//    fun getTotalAmountForSaving(savingId: Int): LiveData<Long> {
//        return repository.getTotalAmountForSaving(savingId).asLiveData()
//    }



    // Memperbarui tabungan yang ada
//    fun updateSaving(savingEntity: SavingEntity) {
//        viewModelScope.launch {
//            repository.updateSaving(savingEntity)
//        }
//    }

    // Menghapus tabungan
//    fun deleteSaving(savingEntity: SavingEntity) {
//        viewModelScope.launch {
//            repository.deleteSaving(savingEntity)
//        }
//    }

    // Menyimpan transaksi baru


    // Menyimpan penarikan baru
//    fun insertWithdrawal(withdrawalEntity: WithdrawalTransactionEntity) {
//        viewModelScope.launch {
//            repository.insertWithdrawal(withdrawalEntity)
//        }
//    }
}
