package org.skender.intime.base

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.flowWithLifecycle
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import timber.log.Timber

inline fun <T> collect(
    lifecycleOwner: LifecycleOwner,
    lifecycleScope: LifecycleCoroutineScope,
    flow: Flow<T>,
    crossinline onChanged: (T) -> Unit,
) {
    flow.flowWithLifecycle(lifecycleOwner.lifecycle, Lifecycle.State.STARTED)
        .onEach { onChanged.invoke(it) }
        .launchIn(lifecycleScope)
}

fun <T> Flow<T>.catchAndLog(onError: (Throwable) -> Unit) = catch {
    Timber.e(it)
    onError.invoke(it)
}

fun <T> Flow<T>.logError() = catch { Timber.e(it) }