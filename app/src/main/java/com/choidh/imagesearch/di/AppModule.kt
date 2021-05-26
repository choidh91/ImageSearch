package com.choidh.imagesearch.di

import android.app.Application
import androidx.room.Room
import com.choidh.imagesearch.api.NaverApi
import com.choidh.imagesearch.data.history.HistoryDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit =
        Retrofit.Builder()
            .baseUrl(NaverApi.BASE_URL)
            .client(NaverApi.client())
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun provideNaverApi(retrofit: Retrofit): NaverApi =
        retrofit.create(NaverApi::class.java)

    @Provides
    @Singleton
    fun provideDatabase(app: Application) =
        Room.databaseBuilder(app, HistoryDatabase::class.java, "history_database")
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    fun provideTaskDao(db: HistoryDatabase) = db.historyDao()
}