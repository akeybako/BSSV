package io.github.akeybako.bssv.ui.user

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.akeybako.bssv.repository.GithubRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.*

@HiltViewModel
class UserViewModel @Inject constructor(
    private val githubRepository: GithubRepository
) : ViewModel() {

    private val userNameSharedFlow = MutableSharedFlow<String>(replay = 1)

    fun fetchUser(username: String) {
        userNameSharedFlow.tryEmit(username)
    }

    val userFlow = userNameSharedFlow.flatMapLatest {
        githubRepository.fetchUser(it)
    }

    val repoFlow = userNameSharedFlow.flatMapLatest {
        githubRepository.fetchRepos(it)
    }
}