package com.choidh.imagesearch.data.history

import androidx.room.Database
import androidx.room.RoomDatabase
import com.choidh.imagesearch.data.history.History
import com.choidh.imagesearch.data.history.HistoryDao

@Database(entities = [History::class], version = 1)
abstract class HistoryDatabase : RoomDatabase() {

    abstract fun historyDao(): HistoryDao

}