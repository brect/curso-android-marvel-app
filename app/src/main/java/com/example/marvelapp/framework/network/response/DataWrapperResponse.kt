package com.example.marvelapp.framework.network.response

import com.google.gson.annotations.SerializedName

data class DataWrapperResponse<T>(
    @SerializedName("data")
    val data: DataContainerResponse<T>,
    @SerializedName("copyright")
    val copyright : String
)
