package com.irgiys.tahasimp.db.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "saving_data")
@Parcelize
data class SavingEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "name")
    var name: String,

    @ColumnInfo(name = "target")
    var target: Long? = null,

    @ColumnInfo(name = "day_target")
    var dayTarget: Int? = null

) : Parcelable