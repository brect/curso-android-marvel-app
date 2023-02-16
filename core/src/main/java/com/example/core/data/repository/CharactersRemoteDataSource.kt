package com.example.core.data.repository

import androidx.paging.PagingSource
import com.example.core.domain.model.Character

interface CharactersRemoteDataSource<T> {

   suspend fun fetchCharacters(queries: Map<String, String>) : T
}