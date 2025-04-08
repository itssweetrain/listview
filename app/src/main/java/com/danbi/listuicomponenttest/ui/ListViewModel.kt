package com.danbi.listuicomponenttest.ui

import androidx.lifecycle.ViewModel
import com.danbi.listuicomponenttest.ListSideEffect
import com.danbi.listuicomponenttest.ListUiState
import com.danbi.listuicomponenttest.Route
import com.danbi.listuicomponenttest.getCards
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update

class ListViewModel : ViewModel() {

    private val _state = MutableStateFlow(ListUiState())
    val state: StateFlow<ListUiState> = _state.asStateFlow()

    private val _effect = Channel<ListSideEffect>()
    val effect = _effect.receiveAsFlow()

    fun getCardList(init: Boolean = true) {
        if (init) {
            _state.update {
                it.copy(list = getCards())
            }
        } else {
            val updated =
                state.value.list.mapIndexed { index, it ->
                    it.copy(
                        name = "New Card $index",
                    )
                }

            _state.update {
                it.copy(list = updated)
            }
        }
    }

    fun clickNavigateToList(route: Route) {
        _effect.trySend(ListSideEffect.NavigateToList(route))
    }
}
