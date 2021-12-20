package com.example.omdbdemo.viewmodels


import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import android.util.Log

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lrucachemvvmdemo.RandomDogApp
import com.example.lrucachemvvmdemo.data.DogImageResult
import com.example.omdbdemo.api.ApiService
import com.example.omdbdemo.util.Resource
import com.example.omdbdemo.util.toMD5
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.URL
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.util.Base64.getEncoder
import javax.inject.Inject


@HiltViewModel
class RandomDogViewModel @Inject constructor(private val apiService: ApiService) : ViewModel() {
    val dogDetail = MutableLiveData<Resource<DogImageResult?>>()

    val dogBitmap = MutableLiveData<Bitmap>()

    private val exceptionHandler = CoroutineExceptionHandler { _, exception ->
        dogDetail.postValue(Resource.error("Something Went Wrong", null))
    }

    private val exceptionHandlerDB = CoroutineExceptionHandler { _, exception ->
    }

    fun fetchDetails() {
        viewModelScope.launch(exceptionHandler) {
            dogDetail.postValue(Resource.loading(null))
            apiService.getRandomDogResult().let {
                if (it.isSuccessful) {
                    dogDetail.postValue(Resource.success(it.body()))
                } else {
                    Log.d("TAG", "Network Call Failed")
                    dogDetail.postValue(Resource.error(it.errorBody().toString(), null))
                }
            }
        }
    }

    fun fetchBitmap(dogImageResult: DogImageResult?) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                 //Base64.getUrlEncoder().encodeToString(originalURL.getBytes())
                val key = dogImageResult?.message?.toMD5()
                if (RandomDogApp.diskLruImageCache?.containsKey(key) == true) {
                    dogBitmap.postValue(RandomDogApp.diskLruImageCache?.getBitmap(dogImageResult?.message))
                } else {
                    val url = URL(dogImageResult?.message);
                    val bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream())
                    dogBitmap.postValue(bitmap);
                    RandomDogApp.diskLruImageCache?.put(key,bitmap)
                }
            }
        }
    }

}