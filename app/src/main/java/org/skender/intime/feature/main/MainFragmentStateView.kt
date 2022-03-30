package org.skender.intime.feature.main

import org.skender.intime.base.ContentViewState
import org.skender.intime.base.ViewState

sealed class MainFragmentStateView: ViewState {
    object Loading: MainFragmentStateView()

    data class Content(
        val string: String = ""
    ): MainFragmentStateView(), ContentViewState

    object Error: MainFragmentStateView()
}