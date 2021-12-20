package io.github.akeybako.bssv.repository

import com.skydoves.sandwich.message
import com.skydoves.sandwich.onError
import com.skydoves.sandwich.onException
import com.skydoves.sandwich.suspendOnSuccess
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.akeybako.bssv.network.GithubService
import io.github.akeybako.bssv.user.Repo
import io.github.akeybako.bssv.user.User
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onCompletion

@Module
@InstallIn(SingletonComponent::class)
class GithubRepository @Inject constructor(
    private val githubService: GithubService,
    private val ioDispatcher: CoroutineDispatcher
) {
    suspend fun fetchUsers(
        q: String,
        onComplete: () -> Unit,
        onError: (String?) -> Unit
    ) = flow {
        val response = githubService.fetchUsers(q)
        response.suspendOnSuccess {
            emit(data)
        }
            .onError { onError(message()) }
            .onException { onError(message) }
    }.onCompletion { onComplete() }.flowOn(ioDispatcher)

    suspend fun fetchUser(
        username: String
    ) = flow {
        val response = githubService.fetchUser(username)
        response.suspendOnSuccess {
            emit(
                User(
                    data.login,
                    data.name,
                    data.avatarUrl,
                    data.followers.toString(),
                    data.following.toString()
                )
            )
        }
            .onError {}
            .onException { }
    }.onCompletion { }.flowOn(ioDispatcher)

    suspend fun fetchRepos(
        username: String
    ) = flow {
        val response = githubService.fetchRepos(username)
        response.suspendOnSuccess {
            data.map {
                Repo(
                    name = it.name,
                    language = it.language ?: "",
                    starCount = it.starCount.toString(),
                    description = it.description ?: "",
                    url = it.htmlUrl,
                    isFork = it.isFork
                )
            }
                .filter { !it.isFork }
                .let {
                    emit(it)
                }
        }
            .onError {}
            .onException { }
    }.onCompletion { }.flowOn(ioDispatcher)
}