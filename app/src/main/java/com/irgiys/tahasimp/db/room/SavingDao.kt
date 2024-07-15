package com.irgiys.tahasimp.db.room

import androidx.lifecycle.LiveData
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

    @Query("SELECT * FROM saving_data ORDER BY date_created DESC")
    fun getAllSavings(): Flow<List<SavingEntity>>

    @Query("SELECT * FROM saving_data WHERE is_completed = false ORDER BY date_created DESC")
    fun getAllProgressSavings(): Flow<List<SavingEntity>>

    @Query("SELECT * FROM saving_data WHERE is_completed = true ORDER BY date_created DESC")
    fun getAllCompletedSavings(): Flow<List<SavingEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTransaction(transaction: HistoryTransactionEntity)

    @Query("SELECT * FROM saving_data WHERE title LIKE :searchQuery ORDER BY date_created DESC")
    fun searchSavings(searchQuery: String): LiveData<List<SavingEntity>>

    @Query("SELECT * FROM saving_data WHERE title LIKE :searchQuery AND is_completed = false ORDER BY date_created DESC")
    fun searchProgressSavings(searchQuery: String): LiveData<List<SavingEntity>>

    @Query("SELECT * FROM saving_data WHERE title LIKE :searchQuery AND is_completed = true ORDER BY date_created DESC")
    fun searchCompletedSavings(searchQuery: String): LiveData<List<SavingEntity>>

    @Query("SELECT * FROM history_transaction WHERE saving_id = :savingId ORDER BY date_created DESC")
    fun getHistoryTransactions(savingId: Int): Flow<List<HistoryTransactionEntity>>

    @Query("SELECT * FROM history_transaction WHERE saving_id = :savingId ORDER BY date_created DESC")
    fun getTransactionsBySavingId(savingId: Int): Flow<List<HistoryTransactionEntity>>

    @Query("SELECT SUM(amount) FROM history_transaction WHERE saving_id = :savingId AND type = 'saving'")
    fun getTotalSavings(savingId: Int): Flow<Long?>

    @Query("SELECT SUM(amount) FROM history_transaction WHERE saving_id = :savingId AND type = 'withdrawal'")
    fun getTotalWithdrawals(savingId: Int): Flow<Long?>

}
