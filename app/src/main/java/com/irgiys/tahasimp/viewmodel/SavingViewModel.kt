package com.irgiys.tahasimp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.irgiys.tahasimp.db.entity.SavingEntity
import com.irgiys.tahasimp.db.entity.SavingTransactionEntity

import com.irgiys.tahasimp.repository.SavingRepository
import kotlinx.coroutines.launch


class SavingViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: SavingRepository = SavingRepository(application)

    // Mengambil semua tabungan
    val allSavings: LiveData<List<SavingEntity>> = repository.getAllSavings().asLiveData()

    // Mendapatkan detail tabungan berdasarkan ID
    fun getSavingById(id: Int): LiveData<SavingEntity> {
        return repository.getSavingById(id).asLiveData()
    }

    // Mengambil semua transaksi tabungan
    fun getTransactionsForSaving(savingId: Int): LiveData<List<SavingTransactionEntity>> {
        return repository.getTransactionsForSaving(savingId).asLiveData()
    }

    // Mengambil semua penarikan tabungan
//    fun getWithdrawalsForSaving(savingId: Int): LiveData<List<WithdrawalTransactionEntity>> {
//        return repository.getWithdrawalsForSaving(savingId).asLiveData()
//    }

    // Mendapatkan total dana yang terkumpul untuk tabungan tertentu
//    fun getTotalAmountForSaving(savingId: Int): LiveData<Long> {
//        return repository.getTotalAmountForSaving(savingId).asLiveData()
//    }

    // Menyimpan tabungan baru
    fun insertSaving(savingEntity: SavingEntity) {
        viewModelScope.launch {
            repository.insertSaving(savingEntity)
        }
    }

    // Memperbarui tabungan yang ada
    fun updateSaving(savingEntity: SavingEntity) {
        viewModelScope.launch {
            repository.updateSaving(savingEntity)
        }
    }

    // Menghapus tabungan
    fun deleteSaving(savingEntity: SavingEntity) {
        viewModelScope.launch {
            repository.deleteSaving(savingEntity)
        }
    }

    // Menyimpan transaksi baru
//    fun insertTransaction(transactionEntity: SavingTransactionEntity) {
//        viewModelScope.launch {
//            repository.insertTransaction(transactionEntity)
//        }
//    }

    // Menyimpan penarikan baru
//    fun insertWithdrawal(withdrawalEntity: WithdrawalTransactionEntity) {
//        viewModelScope.launch {
//            repository.insertWithdrawal(withdrawalEntity)
//        }
//    }
}
