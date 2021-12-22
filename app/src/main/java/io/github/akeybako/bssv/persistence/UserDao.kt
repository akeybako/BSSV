package io.github.akeybako.bssv.persistence

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.github.akeybako.bssv.ui.user.User

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: User)

    @Query("SELECT * FROM User WHERE loginName = :loginName_")
    suspend fun getUser(loginName_: String): User?

    @Query("DELETE FROM User")
    suspend fun nukeUser()
}