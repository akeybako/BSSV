package io.github.akeybako.bssv

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.tooling.preview.Preview
import io.github.akeybako.bssv.search.SearchViewModel
import kotlinx.coroutines.launch

@Composable
fun SearchScreen(
    viewModel: SearchViewModel
) {
    val keyword by viewModel.keyword
    Scaffold(
        topBar = {
            SearchTopBar(
                keyword = keyword,
                keywordChanged = {
                    viewModel.keywordChanged(it)
                },
                searchClicked = {
                    viewModel.searchClicked()
                }
            )
        }
    ) {
    }
}

@Composable
fun SearchTopBar(
    keyword: String,
    keywordChanged: suspend (String) -> Unit,
    searchClicked: suspend () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    TopAppBar(
        title = {
            TextField(
                value = keyword,
                onValueChange = {
                    coroutineScope.launch {
                        keywordChanged(it)
                    }
                },
                placeholder = {
                    Text(text = "input keyword")
                },
                singleLine = true,
                maxLines = 1
            )
        },
        actions = {
            IconButton(
                onClick = {
                    coroutineScope.launch {
                        searchClicked()
                    }
                },
                content = {
                    Icon(Icons.Default.Search, contentDescription = null)
                }
            )
        }
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewSearchTopBar() {
    SearchTopBar(
        keyword = "",
        keywordChanged = {},
        searchClicked = {}
    )
}
