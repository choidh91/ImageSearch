package com.choidh.imagesearch.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.choidh.imagesearch.data.History

@Database(entities = [History::class], version = 3)
abstract class HistoryDatabase : RoomDatabase() {
    abstract fun historyDao(): HistoryDao

    companion object {
        private var INSTANCE: HistoryDatabase? = null

        fun getInstance(context: Context): HistoryDatabase? {
            if (INSTANCE == null) {
                synchronized(HistoryDatabase::class) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        HistoryDatabase::class.java, "history"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }
            return INSTANCE
        }
    }
}