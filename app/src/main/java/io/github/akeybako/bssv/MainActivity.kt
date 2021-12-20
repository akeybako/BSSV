package io.github.akeybako.bssv

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import dagger.hilt.android.AndroidEntryPoint
import io.github.akeybako.bssv.extension.showCustomTab
import io.github.akeybako.bssv.navigation.AppDestination
import io.github.akeybako.bssv.ui.theme.BSSVTheme
import io.github.akeybako.bssv.user.UserScreen
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @ExperimentalFoundationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BSSVTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = AppDestination.Search.route
                ) {
                    composable(
                        route = AppDestination.Search.route,
                        arguments = emptyList()
                    ) { backStackEntry ->
                        SearchScreen(
                            viewModel = hiltViewModel(),
                            navController = navController
                        )
                    }

                    composable(
                        route = AppDestination.User.route,
                        arguments = listOf(navArgument("username") { type = NavType.StringType })
                    ) { backStackEntry ->
                        UserScreen(
                            viewModel = hiltViewModel(),
                            navController = navController,
                            username = backStackEntry.arguments?.getString("username")!!,
                            repoClicked = {
                                showCustomTab(it)
                            }
                        )
                    }
                }
            }
        }
    }
}
