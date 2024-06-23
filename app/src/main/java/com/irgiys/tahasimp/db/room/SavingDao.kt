package com.irgiys.tahasimp.db.room

import androidx.room.*
import com.irgiys.tahasimp.db.entity.SavingEntity
import com.irgiys.tahasimp.db.entity.SavingTransactionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SavingDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSaving(saving: SavingEntity)

    @Update
    suspend fun updateSaving(saving: SavingEntity)

    @Delete
    suspend fun deleteSaving(saving: SavingEntity)

    @Query("SELECT * FROM saving_data WHERE id = :id")
    fun getSavingById(id: Int): Flow<SavingEntity>

    @Query("SELECT * FROM saving_data")
    fun getAllSavings(): Flow<List<SavingEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTransaction(transaction: SavingTransactionEntity)

    @Query("SELECT * FROM saving_transaction WHERE saving_id = :savingId")
    fun getTransactionsForSaving(savingId: Int): Flow<List<SavingTransactionEntity>>
}
