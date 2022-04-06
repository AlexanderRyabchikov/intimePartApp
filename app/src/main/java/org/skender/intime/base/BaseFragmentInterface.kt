package org.skender.intime.base

import android.view.View
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.LifecycleOwner
import androidx.viewbinding.ViewBinding

interface BaseFragmentInterface<SpecificBinding : ViewBinding,
        VM : BaseViewModel<out State, out ContentViewState>,
        State : ViewState> : ViewStateDispatcher<State>, ProgressDialogDispatcher, LifecycleOwner {
    val viewModel: VM

    var _binding: SpecificBinding?

    val binding get() = _binding!!

    fun bindView(view: View): SpecificBinding

    fun viewCreated(view: View, lifecycleOwner: LifecycleOwner, lifecycleCoroutineScope: LifecycleCoroutineScope) {
        _binding = bindView(view)
        collect(
            lifecycleOwner = lifecycleOwner,
            lifecycleScope = lifecycleCoroutineScope,
            flow = viewModel.stateFlow,
            onChanged = ::renderState
        )
        initViews()
    }

    fun destroyView() {
        _binding = null
    }

    @Suppress("OptionalUnit")
    fun initViews() = Unit
}