package com.irgiys.tahasimp.repository

import android.app.Application
import com.irgiys.tahasimp.db.entity.HistoryTransactionEntity
import com.irgiys.tahasimp.db.entity.SavingEntity
import com.irgiys.tahasimp.db.entity.SavingTransactionEntity
import com.irgiys.tahasimp.db.entity.WithdrawalTransactionEntity
import com.irgiys.tahasimp.db.room.SavingDao
import com.irgiys.tahasimp.db.room.SavingDatabase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class SavingRepository(application: Application) {
    private val savingDao: SavingDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = SavingDatabase.getDatabase(application)
        savingDao = db.savingDao()
    }

    fun getAllSavings(): Flow<List<SavingEntity>> = savingDao.getAllSavings()

//    fun getSavingById(id: Int): Flow<SavingEntity> = savingDao.getSavingById(id)

    suspend fun insertSaving(savingEntity: SavingEntity) {
        savingDao.insertSaving(savingEntity)
    }

    suspend fun updateSaving(savingEntity: SavingEntity) {
        savingDao.updateSaving(savingEntity)
    }

    suspend fun deleteSaving(savingEntity: SavingEntity) {
        savingDao.deleteSaving(savingEntity)
    }

    suspend fun insertTransaction(transaction: HistoryTransactionEntity) {
        savingDao.insertTransaction(transaction)
    }

    fun getNetSavings(savingId: Int): Flow<Long> {
        val totalSavingsFlow = savingDao.getTotalSavings(savingId)
        val totalWithdrawalsFlow = savingDao.getTotalWithdrawals(savingId)

        return combine(totalSavingsFlow, totalWithdrawalsFlow) { totalSavings, totalWithdrawals ->
            (totalSavings ?: 0L) - (totalWithdrawals ?: 0L)
        }
    }

    fun getHistoryTransactions(savingId: Int): Flow<List<HistoryTransactionEntity>> {
        return savingDao.getHistoryTransactions(savingId)
    }


//    fun getTransactionsForSaving(savingId: Int): Flow<List<SavingTransactionEntity>> =
//        savingDao.getTransactionsForSaving(savingId)
//
//    fun getWithdrawalsForSaving(savingId: Int): Flow<List<WithdrawalTransactionEntity>> =
//        savingDao.getWithdrawalsForSaving(savingId)

//    suspend fun insertTransactionAndUpdateTotalSaving(transactionEntity: SavingTransactionEntity) {
//        savingDao.insertTransactionAndUpdateTotalSaving(transactionEntity)
//    }
//
//    suspend fun insertWithdrawalAndUpdateTotalSaving(withdrawalEntity: WithdrawalTransactionEntity) {
//        savingDao.insertWithdrawalAndUpdateTotalSaving(withdrawalEntity)
//    }
}
