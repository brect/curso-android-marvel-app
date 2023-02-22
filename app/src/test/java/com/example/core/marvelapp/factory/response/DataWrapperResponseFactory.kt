package com.example.core.marvelapp.factory.response

import com.example.marvelapp.framework.network.response.CharacterResponse
import com.example.marvelapp.framework.network.response.DataContainerResponse
import com.example.marvelapp.framework.network.response.DataWrapperResponse
import com.example.marvelapp.framework.network.response.ThumbnailResponse

class DataWrapperResponseFactory {

    fun create() = DataWrapperResponse(
        copyright = "",
        data = DataContainerResponse(
            offset = 0,
            total = 2,
            results = listOf(
                CharacterResponse(
                    id = "123",
                    name = "Hero 1",
                    thumbnail = ThumbnailResponse(
                        path = "http:sitemarvel.com/1",
                        extension = "jpg"
                    )
                ),
                CharacterResponse(
                    id = "321",
                    name = "Hero 2",
                    thumbnail = ThumbnailResponse(
                        path = "http:sitemarvel.com/2",
                        extension = "jpg"
                    )
                )
            )
        )
    )

}