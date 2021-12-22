package io.github.akeybako.bssv.ui.user

import androidx.room.Entity
import com.squareup.moshi.JsonClass

@Entity
@JsonClass(generateAdapter = true)
data class Repo(
    val name: String,
    val language: String,
    val starCount: String,
    val description: String,
    val url: String,
    val isFork: Boolean
)