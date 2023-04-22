package com.example.marvelapp.framework.db.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.core.data.DbConstants.CHARACTERS_TABLE_NAME
import com.example.core.data.DbConstants.FAVORITES_TABLE_NAME
import com.example.marvelapp.framework.db.entity.CharacterEntity
import com.example.marvelapp.framework.db.entity.FavoriteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CharacterDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(characters: List<CharacterEntity>)

    @Query("SELECT * FROM $CHARACTERS_TABLE_NAME")
    fun pagingSource(): PagingSource<Int, CharacterEntity>

    @Query("DELETE FROM $CHARACTERS_TABLE_NAME")
    fun clearAll()

}