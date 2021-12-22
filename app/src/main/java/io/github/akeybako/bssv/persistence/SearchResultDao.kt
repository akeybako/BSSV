package io.github.akeybako.bssv.persistence

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.github.akeybako.bssv.ui.user.SearchResult

@Dao
interface SearchResultDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSearchResult(list: List<SearchResult>)

    @Query("SELECT * FROM SearchResult WHERE q = :q_")
    suspend fun getAllSearchResult(q_: String): List<SearchResult>

    @Query("DELETE FROM SearchResult")
    suspend fun nukeSearchResult()
}