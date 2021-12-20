package com.example.omdbdemo.viewmodels


import android.app.Application
import android.content.ContentResolver
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.os.Build
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import androidx.core.net.toUri

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lrucachemvvmdemo.RandomDogApp
import com.example.lrucachemvvmdemo.data.DogImageResult
import com.example.lrucachemvvmdemo.repository.ImageCaheRepository
import com.example.omdbdemo.api.ApiService
import com.example.omdbdemo.util.Resource
import com.example.omdbdemo.util.resizeBitmap
import com.example.omdbdemo.util.toMD5
import dagger.hilt.android.internal.Contexts.getApplication
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.lang.Exception
import java.net.URL
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.util.Base64.getEncoder
import javax.inject.Inject


@HiltViewModel
class CachedImagesViewModel @Inject constructor(private val imageCaheRepository: ImageCaheRepository) : ViewModel() {
    val bitmaps = MutableLiveData<Resource<MutableList<Bitmap>>>()

    fun fetchImages() {
        viewModelScope.launch() {
            bitmaps.postValue(Resource.loading(null))
            withContext(Dispatchers.IO) {
                bitmaps.postValue(Resource.success(imageCaheRepository.fetchImages()))
            }
        }
    }


}