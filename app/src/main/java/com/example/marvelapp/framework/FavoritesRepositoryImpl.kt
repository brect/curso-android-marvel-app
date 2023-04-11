package com.example.marvelapp.framework

import com.example.core.data.repository.FavoritesLocalDataSource
import com.example.core.data.repository.FavoritesRepository
import com.example.core.domain.model.Character
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FavoritesRepositoryImpl @Inject constructor(
    private val favoritesLocalDataSource: FavoritesLocalDataSource,
) : FavoritesRepository {
    override suspend fun getAll(): Flow<List<Character>> {
        return favoritesLocalDataSource.getAll()
    }

    override suspend fun saveFavorite(character: Character) {
        favoritesLocalDataSource.saveFavorite(character)
    }

    override suspend fun deleteFavorite(character: Character) {
        favoritesLocalDataSource.deleteFavorite(character)
    }
}