package com.example.marvelapp.framework.network.response

import com.google.gson.annotations.SerializedName

data class DataWrapperResponse(
    @SerializedName("data")
    val data: DataContainerResponse,
    @SerializedName("copyright")
    val copyright : String
)
