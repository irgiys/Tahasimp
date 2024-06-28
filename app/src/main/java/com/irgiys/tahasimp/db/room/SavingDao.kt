package com.irgiys.tahasimp.db.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.irgiys.tahasimp.db.entity.HistoryTransactionEntity
import com.irgiys.tahasimp.db.entity.SavingEntity
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

    @Query("SELECT * FROM saving_data ORDER BY date_created DESC")
    fun getAllSavings(): Flow<List<SavingEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTransaction(transaction: HistoryTransactionEntity)

    @Query("SELECT * FROM history_transaction WHERE saving_id = :savingId ORDER BY date_created DESC")
    fun getTransactionsBySavingId(savingId: Int): Flow<List<HistoryTransactionEntity>>

    @Query("SELECT SUM(amount) FROM history_transaction WHERE saving_id = :savingId AND type = 'saving'")
    fun getTotalSavings(savingId: Int): Flow<Long?>

    @Query("SELECT SUM(amount) FROM history_transaction WHERE saving_id = :savingId AND type = 'withdrawal'")
    fun getTotalWithdrawals(savingId: Int): Flow<Long?>

    @Query("SELECT * FROM history_transaction WHERE saving_id = :savingId ORDER BY date_created DESC")
    fun getHistoryTransactions(savingId: Int): Flow<List<HistoryTransactionEntity>>

//    @Query("SELECT * FROM saving_transaction WHERE saving_id = :savingId")
//    fun getTransactionsForSaving(savingId: Int): Flow<List<SavingTransactionEntity>>
//
//    @Query("SELECT * FROM withdrawal_transaction WHERE saving_id = :savingId")
//    fun getWithdrawalsForSaving(savingId: Int): Flow<List<WithdrawalTransactionEntity>>

//    @Transaction
//    suspend fun insertTransactionAndUpdateTotalSaving(transactionEntity: SavingTransactionEntity) {
//        insertSavingTransaction(transactionEntity)
//        updateTotalSaving(transactionEntity.savingId)
//    }
//    @Transaction
//    suspend fun insertWithdrawalAndUpdateTotalSaving(withdrawalEntity: WithdrawalTransactionEntity) {
//        insertWithdrawalTransaction(withdrawalEntity)
//        updateTotalSaving(withdrawalEntity.savingId)
//    }

//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun insertSavingTransaction(transaction: SavingTransactionEntity)
//
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun insertWithdrawalTransaction(transaction: WithdrawalTransactionEntity)

//    @Query("UPDATE saving_data SET total_saving = (SELECT COALESCE(SUM(amount), 0) FROM saving_transaction WHERE saving_id = :savingId) - (SELECT COALESCE(SUM(amount), 0) FROM withdrawal_transaction WHERE saving_id = :savingId) WHERE id = :savingId")
//    suspend fun updateTotalSaving(savingId: Int)

//    @Query("SELECT COALESCE(SUM(amount), 0) FROM saving_transaction WHERE saving_id = :savingId")
//    suspend fun getTotalDepositsForSaving(savingId: Int): Long

//    @Query("SELECT COALESCE(SUM(amount), 0) FROM withdrawal_transaction WHERE saving_id = :savingId")
//    suspend fun getTotalWithdrawalsForSaving(savingId: Int): Long

}
