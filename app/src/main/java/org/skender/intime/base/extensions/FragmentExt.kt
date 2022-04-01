package org.skender.intime.base.extensions

import androidx.fragment.app.Fragment

@Suppress("UNCHECKED_CAST")
fun <T> Fragment.convertFromActivity(): T = activity as T