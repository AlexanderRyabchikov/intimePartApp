package org.skender.intime.feature.medical_data

import org.skender.intime.base.ContentViewState
import org.skender.intime.base.ViewState

sealed class MedicalDataViewState: ViewState {

    object Loading: MedicalDataViewState()

    data class Content(
        val string: String = ""
    ): MedicalDataViewState(), ContentViewState

    object Error: MedicalDataViewState()
}