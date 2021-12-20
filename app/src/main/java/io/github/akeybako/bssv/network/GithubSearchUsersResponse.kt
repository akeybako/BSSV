package io.github.akeybako.bssv.network

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GithubSearchUsersResponse(
    @Json(name = "total_count") val totalCount: Int,
    @Json(name = "incomplete_results") val incompleteResults: Boolean,
    @Json(name = "items") val items: List<GithubSearchUser>
)

@JsonClass(generateAdapter = true)
data class GithubSearchUser(
    @Json(name = "login") val login: String,
    @Json(name = "avatar_url") val avatarUrl: String
)

@JsonClass(generateAdapter = true)
data class GithubUserResponse(
    @Json(name = "login") val login: String,
    @Json(name = "avatar_url") val avatarUrl: String,
    @Json(name = "name") val name: String,
    @Json(name = "followers") val followers: Int,
    @Json(name = "following") val following: Int
)

@JsonClass(generateAdapter = true)
data class GithubReposResponse(
    @Json(name = "name") val name: String,
    @Json(name = "language") val language: String?,
    @Json(name = "stargazers_count") val starCount: Int,
    @Json(name = "description") val description: String?,
    @Json(name = "html_url") val htmlUrl: String,
    @Json(name = "fork") val isFork: Boolean
)