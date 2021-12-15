package io.github.akeybako.bssv.search

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor() : ViewModel() {
    private val _keyword: MutableState<String> = mutableStateOf("")
    val keyword: State<String>
        get() = _keyword

    suspend fun keywordChanged(newKeyword: String) {
        _keyword.value = newKeyword
    }

    suspend fun searchClicked() {

    }
}