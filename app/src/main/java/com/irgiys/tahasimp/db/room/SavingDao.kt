package com.irgiys.tahasimp.db.room

import androidx.room.*
import com.irgiys.tahasimp.db.entity.SavingEntity
import com.irgiys.tahasimp.db.entity.SavingTransactionEntity
import com.irgiys.tahasimp.db.entity.WithdrawalTransactionEntity
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

    @Query("SELECT * FROM saving_transaction WHERE saving_id = :savingId")
    fun getTransactionsForSaving(savingId: Int): Flow<List<SavingTransactionEntity>>

    @Query("SELECT * FROM withdrawal_transaction WHERE saving_id = :savingId")
    fun getWithdrawalsForSaving(savingId: Int): Flow<List<WithdrawalTransactionEntity>>

    @Transaction
    suspend fun insertTransactionAndUpdateTotalSaving(transactionEntity: SavingTransactionEntity) {
        insertSavingTransaction(transactionEntity)
        updateTotalSaving(transactionEntity.savingId)
    }
    @Transaction
    suspend fun insertWithdrawalAndUpdateTotalSaving(withdrawalEntity: WithdrawalTransactionEntity) {
        insertWithdrawalTransaction(withdrawalEntity)
        updateTotalSaving(withdrawalEntity.savingId)
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSavingTransaction(transaction: SavingTransactionEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWithdrawalTransaction(transaction: WithdrawalTransactionEntity)

    @Query("UPDATE saving_data SET total_saving = (SELECT COALESCE(SUM(amount), 0) FROM saving_transaction WHERE saving_id = :savingId) - (SELECT COALESCE(SUM(amount), 0) FROM withdrawal_transaction WHERE saving_id = :savingId) WHERE id = :savingId")
    suspend fun updateTotalSaving(savingId: Int)

    @Query("SELECT COALESCE(SUM(amount), 0) FROM saving_transaction WHERE saving_id = :savingId")
    suspend fun getTotalDepositsForSaving(savingId: Int): Long

    @Query("SELECT COALESCE(SUM(amount), 0) FROM withdrawal_transaction WHERE saving_id = :savingId")
    suspend fun getTotalWithdrawalsForSaving(savingId: Int): Long

}
