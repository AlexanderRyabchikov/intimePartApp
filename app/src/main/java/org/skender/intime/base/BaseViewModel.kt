package org.skender.intime.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

abstract class BaseViewModel<State : ViewState, ContentState : ContentViewState>(
    initialStateAndDefaultContentState: () -> Pair<State, ContentState>
) : ViewModel() {

    private val _viewStateFlow: MutableStateFlow<State> =
        MutableStateFlow(initialStateAndDefaultContentState().first)

    val stateFlow: StateFlow<State> = _viewStateFlow

    var lastContentState: ContentState = initialStateAndDefaultContentState().second
        private set

    /**
     * Установить новый [ViewState]. Изменение отразится в [stateFlow].
     */
    protected fun updateState(newState: State) {
        @Suppress("UNCHECKED_CAST")
        if (newState is ContentViewState) {
            lastContentState = newState as ContentState
        }

        _viewStateFlow.value = newState
    }
}