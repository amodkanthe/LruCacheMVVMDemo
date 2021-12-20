package com.example.lrucachemvvmdemo.repository

import android.content.ContentResolver
import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.os.Build
import android.provider.MediaStore
import androidx.core.net.toUri
import com.example.lrucachemvvmdemo.RandomDogApp
import com.example.omdbdemo.util.resizeBitmap
import java.io.File
import java.lang.Exception
import javax.inject.Inject

class ImageCaheRepository constructor(private val context: Context?) {

    fun fetchImages() : MutableList<Bitmap> {
        val dir = File(RandomDogApp.diskLruImageCache?.cacheFolder?.toURI())
        var items : MutableList<Bitmap> = arrayListOf()
        if (dir.exists()) {
            for (f in dir.listFiles()) {
                var bitmap: Bitmap? = null
                val contentResolver: ContentResolver? = context?.getContentResolver()
                try {
                    bitmap = if (Build.VERSION.SDK_INT < 28) {
                        MediaStore.Images.Media.getBitmap(contentResolver, f.toUri())
                    } else {
                        contentResolver?.let {
                            val source = ImageDecoder.createSource(it, f.toUri())
                            ImageDecoder.decodeBitmap(source)
                        } ?: run {
                            null
                        }

                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                val scaledBitmap = bitmap?.let { resizeBitmap(it, 240, 240) };
                scaledBitmap?.let {
                    items.add(it)
                }
            }
        }
        return items
    }



}