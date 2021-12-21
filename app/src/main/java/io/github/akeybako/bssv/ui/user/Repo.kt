package io.github.akeybako.bssv.ui.user

data class Repo(
    val name: String,
    val language: String,
    val starCount: String,
    val description: String,
    val url: String,
    val isFork: Boolean
)