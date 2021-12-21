package io.github.akeybako.bssv.ui.search

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import io.github.akeybako.bssv.navigation.AppDestination
import io.github.akeybako.bssv.network.GithubSearchUser
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SearchScreen(
    viewModel: SearchViewModel,
    navController: NavController
) {
    val keyword by viewModel.keyword
    val users by viewModel.userFlow.collectAsState(initial = listOf())
    Scaffold(
        topBar = {
            SearchTopBar(
                modifier = Modifier.fillMaxWidth(),
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
        LazyVerticalGrid(
            cells = GridCells.Fixed(count = 2)
        ) {
            items(users) {
                UserItem(
                    modifier = Modifier
                        .clickable {
                            navController.navigate("${AppDestination.User.route}/${it.login}")
                        },
                    user = it
                )
            }
        }
    }
}

@Composable
fun SearchTopBar(
    modifier: Modifier = Modifier,
    keyword: String,
    keywordChanged: suspend (String) -> Unit,
    searchClicked: suspend () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    ConstraintLayout(
        modifier = modifier
    ) {
        val (
            textFieldRef,
            actionRef
        ) = createRefs()

        TextField(
            modifier = Modifier.constrainAs(textFieldRef) {
                start.linkTo(parent.start)
                top.linkTo(parent.top)
                end.linkTo(actionRef.start)
                height = Dimension.wrapContent
                width = Dimension.fillToConstraints
            },
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

        IconButton(
            modifier = Modifier.constrainAs(actionRef) {
                end.linkTo(parent.end)
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
            },
            onClick = {
                coroutineScope.launch {
                    searchClicked()
                }
            },
            content = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = null
                )
            },
            enabled = keyword.isNotBlank()
        )
    }
}

@Composable
fun UserItem(
    modifier: Modifier = Modifier,
    user: GithubSearchUser
) {
    ConstraintLayout(
        modifier = modifier.padding(all = 8.dp)
    ) {
        val (avatarRef, loginNameRef) = createRefs()

        Image(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .clip(CircleShape)
                .constrainAs(avatarRef) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
            painter = rememberImagePainter(data = user.avatarUrl),
            contentDescription = null
        )

        Text(
            modifier = Modifier.constrainAs(loginNameRef) {
                start.linkTo(avatarRef.start)
                end.linkTo(avatarRef.end)
                top.linkTo(avatarRef.bottom)
            },
            text = user.login,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSearchTopBar() {
    SearchTopBar(
        modifier = Modifier.fillMaxWidth(),
        keyword = "",
        keywordChanged = {},
        searchClicked = {}
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewUserItem() {
    UserItem(
        modifier = Modifier.width(100.dp),
        user = GithubSearchUser(login = "aaa", avatarUrl = "aaaa")
    )
}