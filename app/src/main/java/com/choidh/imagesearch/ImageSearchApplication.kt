package com.choidh.imagesearch

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/* 모든 의존성 주입의 시작점
ApplicationComponent 코드를 생성 및 인스턴스화
 */
@HiltAndroidApp
class ImageSearchApplication : Application() {
}