package com.irgiys.tahasimp.db.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.util.Date

@Entity(tableName = "saving_data")
@Parcelize
data class SavingEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0,

    @ColumnInfo(name = "name")
    var title: String? = null,

    @ColumnInfo(name = "target")
    var target: Long? = null,

    @ColumnInfo(name = "daily_target")
    var dailyTarget: Long? = null,

    @ColumnInfo(name = "day_target")
    var dayTarget: Int? = null,

    @ColumnInfo(name = "date_created")
    var dateCreated: Date = Date(),

//    @ColumnInfo(name = "total_saving")
//    var totalSaving: Long = 0

) : Parcelable