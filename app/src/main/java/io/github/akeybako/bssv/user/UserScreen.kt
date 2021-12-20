package io.github.akeybako.bssv.user

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import io.github.akeybako.bssv.R
import io.github.akeybako.bssv.ui.theme.Typography

@Composable
fun UserScreen(
    viewModel: UserViewModel,
    navController: NavController,
    username: String,
    repoClicked: (String) -> Unit
) {
    val user by viewModel.userFlow.collectAsState(initial = User())
    val repos by viewModel.repoFlow.collectAsState(initial = listOf())

    LaunchedEffect(key1 = username) {
        viewModel.fetchUser(username)
    }

    Scaffold(
        topBar = {}
    ) {
        UserContent(
            modifier = Modifier.fillMaxSize(),
            user = user,
            repos = repos,
            repoClicked = repoClicked
        )
    }
}

@Composable
fun UserTopAppBar(
    login: String
) {
    TopAppBar(
        title = {
            Text(text = login)
        }
    )
}

@Composable
fun UserContent(
    modifier: Modifier = Modifier,
    user: User,
    repos: List<Repo>,
    repoClicked: (String) -> Unit
) {
    ConstraintLayout(modifier = modifier) {
        val (
            avatarRef,
            loginRef,
            nameRef,
            followerRef,
            followingRef,
            reposRef
        ) = createRefs()

        Image(
            modifier = Modifier
                .size(60.dp)
                .clip(CircleShape)
                .constrainAs(avatarRef) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                },
            painter = rememberImagePainter(data = user.avatarUrl),
            contentDescription = null
        )

        Text(
            modifier = Modifier.constrainAs(loginRef) {
                start.linkTo(avatarRef.end)
                top.linkTo(parent.top)
            },
            text = user.loginName,
            style = Typography.h5
        )

        Text(
            modifier = Modifier.constrainAs(nameRef) {
                bottom.linkTo(avatarRef.bottom)
                start.linkTo(avatarRef.end)
            },
            text = user.fullName,
            style = Typography.h6
        )

        Text(
            modifier = Modifier.constrainAs(followerRef) {
                top.linkTo(avatarRef.bottom, margin = 8.dp)
                start.linkTo(parent.start)
            },
            text = stringResource(
                id = R.string.follower,
                user.follower
            )
        )

        Text(
            modifier = Modifier.constrainAs(followingRef) {
                top.linkTo(followerRef.top)
                start.linkTo(followerRef.end, margin = 8.dp)
            },
            text = stringResource(
                id = R.string.following,
                user.following
            )
        )

        Column(
            modifier = Modifier
                .constrainAs(reposRef) {
                    top.linkTo(followerRef.bottom)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    width = Dimension.fillToConstraints
                    height = Dimension.fillToConstraints
                }
                .verticalScroll(
                    state = rememberScrollState()
                )
        ) {
            repos.forEach { repo ->
                RepoCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { repoClicked(repo.url) },
                    repo = repo
                )
            }
        }
    }
}

@Composable
fun RepoCard(
    modifier: Modifier = Modifier,
    repo: Repo
) {
    Card(
        modifier = modifier
            .padding(all = 8.dp)
    ) {
        ConstraintLayout(
            modifier = modifier
                .padding(all = 8.dp)
        ) {

            val (
                nameRef,
                descriptionRef,
                startCountRef,
                languageRef
            ) = createRefs()

            Text(
                modifier = Modifier.constrainAs(nameRef) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(startCountRef.start, margin = 8.dp)
                    width = Dimension.fillToConstraints
                },
                text = repo.name,
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                textAlign = TextAlign.Start
            )

            RepoStarCount(
                modifier = Modifier.constrainAs(startCountRef) {
                    top.linkTo(nameRef.top)
                    bottom.linkTo(nameRef.bottom)
                    end.linkTo(parent.end)
                },
                repo = repo
            )

            RepoDescription(
                modifier = Modifier.constrainAs(descriptionRef) {
                    top.linkTo(nameRef.bottom, margin = 8.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    width = Dimension.fillToConstraints
                    height = Dimension.preferredWrapContent
                },
                description = repo.description
            )

            RepoLaunguage(
                modifier = Modifier
                    .constrainAs(languageRef) {
                        top.linkTo(descriptionRef.bottom, margin = 4.dp)
                        start.linkTo(parent.start)
                    },
                language = repo.language
            )
        }
    }
}

@Composable
fun RepoStarCount(
    modifier: Modifier = Modifier,
    repo: Repo
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Default.Star,
            contentDescription = null,
            tint = Color.Yellow
        )

        Text(
            text = repo.starCount,
        )
    }
}

@Composable
fun RepoDescription(
    modifier: Modifier = Modifier,
    description: String
) {
    val textModifier = when {
        description.isNotBlank() -> modifier
        else -> modifier.height(0.dp)
    }
    Text(
        modifier = textModifier,
        text = description,
        overflow = TextOverflow.Ellipsis,
        maxLines = 1
    )
}

@Composable
fun RepoLaunguage(
    modifier: Modifier = Modifier,
    language: String
) {
    when {
        language.isNotBlank() -> modifier
            .clip(CircleShape)
            .background(color = Color.Gray)
            .padding(
                horizontal = 8.dp,
                vertical = 4.dp
            )
        else -> modifier.height(0.dp)
    }.let {
        Text(
            modifier = it
                .clip(CircleShape)
                .background(color = Color.Gray)
                .padding(
                    horizontal = 8.dp,
                    vertical = 4.dp
                ),
            text = language
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewUserTopAppBar() {
    UserTopAppBar(login = "akeybako")
}

@Preview(showBackground = true)
@Composable
fun PreviewUserContent() {
    UserContent(
        modifier = Modifier.fillMaxSize(),
        user = User(
            loginName = "akeybako",
            fullName = "akito hagio",
            avatarUrl = "www.example.com",
            follower = "10",
            following = "20"
        ),
        repos = listOf(
            Repo(
                name = "name",
                language = "language",
                starCount = "100",
                description = "リポジトリの説明文リポジトリの説明文リポジトリの説明文リポジトリの説明文リポジトリの説明文リポジトリの説明文リポジトリの説明文",
                url = "url",
                isFork = true
            ),
            Repo(
                name = "name",
                language = "language",
                starCount = "100",
                description = "",
                url = "url",
                isFork = true
            ),
            Repo(
                name = "name",
                language = "",
                starCount = "100",
                description = "リポジトリの説明文リポジトリの説明文リポジトリの説明文リポジトリの説明文リポジトリの説明文リポジトリの説明文リポジトリの説明文",
                url = "url",
                isFork = true
            )
        ),
        repoClicked = {}
    )
}


@Composable
@Preview(showBackground = true)
fun PreviewRepoCard() {
    RepoCard(
        modifier = Modifier.fillMaxWidth(),
        repo = Repo(
            name = "name",
            language = "language",
            starCount = "100",
            description = "description",
            url = "url",
            isFork = false
        )
    )
}

@Composable
@Preview(showBackground = true)
fun PreviewRepoStarCount() {
    RepoStarCount(
        modifier = Modifier.fillMaxWidth(),
        repo = Repo(
            name = "name",
            language = "language",
            starCount = "100",
            description = "description",
            url = "url",
            isFork = false
        )
    )
}
