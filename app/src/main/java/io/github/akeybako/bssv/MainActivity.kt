package io.github.akeybako.bssv

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import io.github.akeybako.bssv.search.SearchViewModel
import io.github.akeybako.bssv.ui.theme.BSSVTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BSSVTheme {
                val viewModel by viewModels<SearchViewModel>()
                SearchScreen(
                    viewModel = viewModel
                )
            }
        }
    }
}
