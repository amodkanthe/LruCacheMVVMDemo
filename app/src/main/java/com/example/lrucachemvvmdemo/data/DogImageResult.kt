package com.example.lrucachemvvmdemo.data

import com.google.gson.annotations.SerializedName


data class DogImageResult (

  @SerializedName("message" ) var message : String? = null,
  @SerializedName("status"  ) var status  : String? = null

)