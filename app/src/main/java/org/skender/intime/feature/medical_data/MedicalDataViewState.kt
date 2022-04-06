package org.skender.intime.feature.medical_data

import org.skender.intime.base.ContentViewState
import org.skender.intime.base.ViewState
import org.skender.intime.feature.medical_data.domain.data.MedicalDataInfo
import java.lang.Exception

sealed class MedicalDataViewState: ViewState {

    object Loading: MedicalDataViewState()

    data class Content (
        val heartRate: MedicalDataInfo? = null,
        val sleepTime: MedicalDataInfo? = null,
        val bloodPressure: MedicalDataInfo? = null,
        val bmi: MedicalDataInfo? = null,
        val mainRiskLevel: MedicalDataInfo? = null,
        val weight: MedicalDataInfo? = null,
        val cholesterol: MedicalDataInfo? = null,
        val glucose: MedicalDataInfo? = null
    ): MedicalDataViewState(), ContentViewState

    data class Error(val throwable: Throwable) : MedicalDataViewState()
}