package com.danbi.listuicomponenttest

sealed interface ListSideEffect {
    data class NavigateToList(val route: Route) : ListSideEffect
}

enum class Route {
    RECYCLERVIEW, LIST_ADAPTER, LAZY_COLUMN,
}
