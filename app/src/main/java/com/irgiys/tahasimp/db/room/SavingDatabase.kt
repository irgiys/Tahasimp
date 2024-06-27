package com.irgiys.tahasimp.db.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.irgiys.tahasimp.db.entity.HistoryTransactionEntity

import com.irgiys.tahasimp.db.entity.SavingEntity
import com.irgiys.tahasimp.db.entity.SavingTransactionEntity
import com.irgiys.tahasimp.db.entity.WithdrawalTransactionEntity
import com.irgiys.tahasimp.utils.DateConverter

@Database(entities = [SavingEntity::class, HistoryTransactionEntity::class], version = 1, exportSchema = false)
@TypeConverters(DateConverter::class)
abstract class SavingDatabase : RoomDatabase() {
    abstract fun savingDao(): SavingDao

    companion object {
        @Volatile
        private var INSTANCE: SavingDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context): SavingDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    SavingDatabase::class.java,
                    "saving_database"
                ).build().also {
                    INSTANCE = it
                }
            }
        }
    }
}
