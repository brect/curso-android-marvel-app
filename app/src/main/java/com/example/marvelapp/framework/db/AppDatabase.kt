package com.example.marvelapp.framework.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.marvelapp.framework.db.dao.CharacterDAO
import com.example.marvelapp.framework.db.dao.FavoriteDAO
import com.example.marvelapp.framework.db.entity.CharacterEntity
import com.example.marvelapp.framework.db.entity.FavoriteEntity
import com.example.marvelapp.framework.db.entity.RemoteKey

@Database(
    entities = [
        FavoriteEntity::class,
        CharacterEntity::class,
        RemoteKey::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun favoriteDao(): FavoriteDAO
    abstract fun characterDao(): CharacterDAO
    abstract fun remoteKey(): RemoteKey

}