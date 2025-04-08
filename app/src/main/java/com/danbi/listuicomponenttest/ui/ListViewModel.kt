package com.danbi.listuicomponenttest.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import com.danbi.listuicomponenttest.Card
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
                        description = "New This is card number $index",
                        date = "New 2025-03-31",
                        category = "New Category ${index % 10}",
                        address = "New $index",
                        phone = "New 123-456-$index",
                        email = "New card$index@example.com",
                        price = (index * 0.1) + 1000,
                        imageUrl = "New",
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
