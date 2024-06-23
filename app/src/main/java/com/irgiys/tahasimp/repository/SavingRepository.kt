package com.irgiys.tahasimp.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.irgiys.tahasimp.db.entity.SavingEntity
import com.irgiys.tahasimp.db.entity.SavingTransactionEntity
import com.irgiys.tahasimp.db.room.SavingDao
import com.irgiys.tahasimp.db.room.SavingDatabase
import kotlinx.coroutines.flow.Flow
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

    fun getSavingById(id: Int): Flow<SavingEntity> = savingDao.getSavingById(id)

    fun getTransactionsForSaving(savingId: Int): Flow<List<SavingTransactionEntity>> = savingDao.getTransactionsForSaving(savingId)
    suspend fun insertSaving(savingEntity: SavingEntity) {
        savingDao.insertSaving(savingEntity)
    }

    suspend fun updateSaving(savingEntity: SavingEntity) {
        savingDao.updateSaving(savingEntity)
    }

    suspend fun deleteSaving(savingEntity: SavingEntity) {
        savingDao.deleteSaving(savingEntity)
    }

    suspend fun insertTransaction(transactionEntity: SavingTransactionEntity) {
        savingDao.insertTransaction(transactionEntity)
    }
}
