package io.github.akeybako.bssv.repository

import com.skydoves.sandwich.suspendOnSuccess
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.akeybako.bssv.network.GithubService
import io.github.akeybako.bssv.persistence.SearchResultDao
import io.github.akeybako.bssv.persistence.UserDao
import io.github.akeybako.bssv.ui.user.Repo
import io.github.akeybako.bssv.ui.user.SearchResult
import io.github.akeybako.bssv.ui.user.User
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

@Module
@InstallIn(SingletonComponent::class)
class GithubRepository @Inject constructor(
    private val githubService: GithubService,
    private val searchResultDao: SearchResultDao,
    private val userDao: UserDao,
    private val ioDispatcher: CoroutineDispatcher
) {
    suspend fun fetchUsers(
        q: String
    ) = flow {
        val searchResult = searchResultDao.getAllSearchResult(q)
        if (searchResult.isEmpty()) {
            val response = githubService.fetchUsers(q)
            response.suspendOnSuccess {
                data.items.map {
                    SearchResult(
                        name = it.login,
                        avatarUrl = it.avatarUrl,
                        q = q
                    )
                }.let {
                    searchResultDao.nukeSearchResult()
                    searchResultDao.insertSearchResult(it)
                    emit(it)
                }
            }
        } else {
            emit(searchResult)
        }
    }.flowOn(ioDispatcher)

    suspend fun fetchUser(
        username: String
    ) = flow {
        var user = userDao.getUser(username)
        if (user == null) {
            val response = githubService.fetchUser(username)
            response.suspendOnSuccess {
                user = User(
                    data.login,
                    data.name,
                    data.avatarUrl,
                    data.followers.toString(),
                    data.following.toString()
                )
                userDao.nukeUser()
                userDao.insertUser(user!!)
                emit(user!!)
            }
        } else {
            emit(user!!)
        }
    }.flowOn(ioDispatcher)

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
    }.flowOn(ioDispatcher)
}