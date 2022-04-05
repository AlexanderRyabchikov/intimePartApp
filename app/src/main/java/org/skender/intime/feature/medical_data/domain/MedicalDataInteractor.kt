package org.skender.intime.feature.medical_data.domain

import org.skender.intime.feature.medical_data.domain.data.MedicalData
import org.skender.intime.feature.medical_data.domain.data.MedicalDataInfo
import org.skender.intime.feature.medical_data.network.MedicalDataRepository
import javax.inject.Inject

class MedicalDataInteractor @Inject constructor(private val medicalDataRepository: MedicalDataRepository) {

    suspend fun onLoadData(): List<MedicalDataInfo> {
        return medicalDataRepository.onLoadData()
    }

    suspend fun onSaveData(data: MedicalData) {
        medicalDataRepository.onSaveData(data)
    }

}