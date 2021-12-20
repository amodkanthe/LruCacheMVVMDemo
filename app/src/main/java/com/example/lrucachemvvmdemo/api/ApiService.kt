package com.example.omdbdemo.api


import com.example.lrucachemvvmdemo.data.DogImageResult
import kotlinx.coroutines.Deferred
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiService {

    @GET("random")
    suspend fun getRandomDogResult(): Response<DogImageResult>


}