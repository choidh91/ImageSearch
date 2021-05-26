package com.choidh.imagesearch.data.history

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "history_table")
data class History(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "keyword") val keyword: String
)