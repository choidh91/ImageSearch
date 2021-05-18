package com.choidh.imagesearch.data

import android.app.Application

import androidx.lifecycle.LiveData
import com.choidh.imagesearch.database.HistoryDao
import com.choidh.imagesearch.database.HistoryDatabase
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers


class HistoryRepository(application: Application) {

    private val historyDatabase = HistoryDatabase.getInstance(application)!!
    private val historyDao: HistoryDao = historyDatabase.historyDao()
    private val history: LiveData<List<History>> = historyDao.getAll()

    fun getAll(): LiveData<List<History>> {
        return history;
    }

    fun insert(history: History) {
        Observable.just(history)
            .subscribeOn(Schedulers.io())
            .subscribe({
                historyDao.insertHistory(history)
            }, {

            })
    }

    fun delete(keyword: String) {
        Observable.just(keyword)
            .subscribeOn(Schedulers.io())
            .subscribe({
                historyDao.delete(keyword)
            }, {

            })
    }

}