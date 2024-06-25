package com.irgiys.tahasimp.db.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.util.Date

@Entity(
    tableName = "withdrawal_transaction",
    foreignKeys = [ForeignKey(
        entity = SavingEntity::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("saving_id"),
        onDelete = ForeignKey.CASCADE
    )]
)

@Parcelize
data class WithdrawalTransactionEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0,

    @ColumnInfo(name = "saving_id")
    var savingId: Int,

    @ColumnInfo(name = "date")
    var date: Date = Date(),

    @ColumnInfo(name = "amount")
    var amount: Long
) : Parcelable
