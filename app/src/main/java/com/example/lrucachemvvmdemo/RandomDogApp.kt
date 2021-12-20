package com.example.lrucachemvvmdemo

import android.app.Application
import android.graphics.Bitmap
import com.example.lrucachemvvmdemo.cache.DiskLruImageCache
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class RandomDogApp : Application() {


    companion object {
        var diskLruImageCache: DiskLruImageCache? = null
        const val UNIQUE_NAME = "DOG_CACHE"
        const val CACHE_SIZE = 10 * 1024 * 1024
        const val COMPRESS_QUALITY = 70;
    }

    override fun onCreate() {
        super.onCreate()
        intitCache()
    }

    fun intitCache(){
        diskLruImageCache = DiskLruImageCache(this, UNIQUE_NAME, CACHE_SIZE,
            Bitmap.CompressFormat.PNG, COMPRESS_QUALITY);
    }

}