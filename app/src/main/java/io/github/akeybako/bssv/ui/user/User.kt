package io.github.akeybako.bssv.ui.user

import androidx.room.Entity
import com.squareup.moshi.JsonClass

@Entity(primaryKeys = ["loginName"])
@JsonClass(generateAdapter = true)
data class User(
    val loginName: String = "",
    val fullName: String = "",
    val avatarUrl: String = "",
    val follower: String = "",
    val following: String = ""
)