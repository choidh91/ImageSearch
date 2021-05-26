package com.choidh.imagesearch.data.history

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface HistoryDao {
    @Query("SELECT * FROM history_table WHERE keyword LIKE '%' || :searchQuery || '%' ORDER BY id DESC")
    fun getHistories(searchQuery: String): Flow<List<History>>

//    @Query("SELECT * FROM history_table ORDER BY id DESC")
//    fun getHistories(): LiveData<List<History>>

    @Insert
    suspend fun insert(history: History)

    @Query("DELETE FROM history_table WHERE keyword = :keyword")
    suspend fun delete(keyword: String)
}