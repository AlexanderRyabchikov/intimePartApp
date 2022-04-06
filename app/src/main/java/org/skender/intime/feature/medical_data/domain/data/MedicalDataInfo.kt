package org.skender.intime.feature.medical_data.domain.data

import org.skender.intime.base.extensions.EMPTY

data class MedicalDataInfo (
    val listTypeData: List<String> = emptyList(),
    val value: Any = String.EMPTY,
    val unit: String = String.EMPTY,
    val type: TypeMedicalData
)