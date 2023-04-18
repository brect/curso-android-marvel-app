package com.example.marvelapp.framework.local

import com.example.core.data.repository.FavoritesLocalDataSource
import com.example.core.domain.model.Character
import com.example.marvelapp.framework.db.dao.FavoriteDAO
import com.example.marvelapp.framework.db.entity.FavoriteEntity
import com.example.marvelapp.framework.db.entity.toCharacterModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RoomFavoritesDataSource @Inject constructor(
    private val favoritesDAO: FavoriteDAO
) : FavoritesLocalDataSource {

    override fun getAll(): Flow<List<Character>> {
        return favoritesDAO.loadFavorites().map {
            it.toCharacterModel()
        }
    }

    override suspend fun isFavorite(characterId: Int): Boolean {
        return favoritesDAO.hasFavorite(characterId) != null
    }

    override suspend fun saveFavorite(character: Character) {
        favoritesDAO.insertFavorite(character.toFavoriteEntity())
    }

    override suspend fun deleteFavorite(character: Character) {
        favoritesDAO.insertFavorite(character.toFavoriteEntity())
    }

    private fun Character.toFavoriteEntity() = FavoriteEntity(id, name, imageUrl)
}