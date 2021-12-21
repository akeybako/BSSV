package io.github.akeybako.bssv.ui.user

data class User(
    val loginName: String = "",
    val fullName: String = "",
    val avatarUrl: String = "",
    val follower: String = "",
    val following: String = ""
)