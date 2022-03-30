package org.skender.intime.base

interface ViewStateDispatcher<State : ViewState> {
    fun renderState(state: State)
}