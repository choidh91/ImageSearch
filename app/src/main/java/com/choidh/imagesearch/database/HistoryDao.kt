package com.choidh.imagesearch.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.choidh.imagesearch.data.History

@Dao
interface HistoryDao {
    @Query("SELECT * FROM history ORDER BY uid DESC")
    fun getAll(): LiveData<List<History>>

    @Insert
    fun insertHistory(history: History)

    @Query("DELETE FROM history WHERE keyword = :keyword")
    fun delete(keyword: String)
}