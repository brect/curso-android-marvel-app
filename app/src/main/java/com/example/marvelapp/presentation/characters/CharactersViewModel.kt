package com.example.marvelapp.presentation.characters

import androidx.lifecycle.ViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.LiveData
import androidx.lifecycle.switchMap
import androidx.lifecycle.distinctUntilChanged
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.asLiveData
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.core.domain.model.Character
import com.example.core.usecase.GetCharactersUseCase
import com.example.core.usecase.base.CoroutinesDispatchers
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class CharactersViewModel @Inject constructor(
    private val getCharactersUseCase: GetCharactersUseCase,
    coroutinesDispatchers: CoroutinesDispatchers,
) : ViewModel() {

    private val action = MutableLiveData<Action>()

    val state: LiveData<UiState> = action
        .distinctUntilChanged()
        .switchMap { action ->
            when (action) {
                is Action.Search -> {
                    getCharactersUseCase.invoke(
                        GetCharactersUseCase.GetCharactersParams(action.query, getPageConfig())
                    ).cachedIn(viewModelScope).map {
                        UiState.SearchResult(it)
                    }.asLiveData(coroutinesDispatchers.main())
                }
            }
        }

    fun charactersPagingData(query: String): Flow<PagingData<Character>> {
        return getCharactersUseCase.invoke(
            GetCharactersUseCase.GetCharactersParams(query, getPageConfig())
        ).cachedIn(viewModelScope)
    }

    private fun getPageConfig() = PagingConfig(
        pageSize = 20
    )

    fun searchCharacters(query: String = "") {
        action.value = Action.Search(query)
    }

    sealed class UiState {
        data class SearchResult(val data: PagingData<Character>) : UiState()
    }

    sealed class Action {
        data class Search(val query: String) : Action()
    }
}