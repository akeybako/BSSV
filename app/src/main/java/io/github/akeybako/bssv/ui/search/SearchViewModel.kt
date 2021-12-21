package io.github.akeybako.bssv.ui.search

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.akeybako.bssv.repository.GithubRepository
import javax.inject.Inject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val githubRepository: GithubRepository
) : ViewModel() {
    private val _keyword: MutableState<String> = mutableStateOf("")
    val keyword: State<String>
        get() = _keyword

    private val keywordSharedFlow = MutableSharedFlow<String>(replay = 1)

    fun keywordChanged(newKeyword: String) {
        _keyword.value = newKeyword
    }

    fun searchClicked() {
        keywordSharedFlow.tryEmit(_keyword.value)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    val userFlow = keywordSharedFlow.flatMapLatest {
        githubRepository.fetchUsers(
            q = it
        )
    }
}