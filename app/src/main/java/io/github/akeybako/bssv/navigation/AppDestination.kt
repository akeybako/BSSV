package io.github.akeybako.bssv.navigation

sealed class AppDestination(val route: String) {
    /**
     * 検索画面
     */
    object Search : AppDestination("search")

    /**
     * ユーザー画面
     */
    object User : AppDestination("users") {
        const val routeWithArgument: String = "users/{username}"

        const val argument0: String = "username"
    }
}