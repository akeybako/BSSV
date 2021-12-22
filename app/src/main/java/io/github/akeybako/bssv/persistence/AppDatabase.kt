package io.github.akeybako.bssv.persistence

import androidx.room.Database
import androidx.room.RoomDatabase
import io.github.akeybako.bssv.ui.user.SearchResult
import io.github.akeybako.bssv.ui.user.User

@Database(entities = [SearchResult::class, User::class], version = 1, exportSchema = true)
abstract class AppDatabase : RoomDatabase() {
    abstract fun searchResultDao(): SearchResultDao

    abstract fun userDao(): UserDao
}