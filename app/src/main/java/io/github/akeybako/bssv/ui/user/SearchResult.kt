package io.github.akeybako.bssv.ui.user

import androidx.room.Entity
import com.squareup.moshi.JsonClass

@Entity(primaryKeys = ["name"])
@JsonClass(generateAdapter = true)
data class SearchResult(
    val name: String,
    val avatarUrl: String,
    val q: String = ""
)
