package org.skender.intime.feature.medical_data.network

import org.skender.intime.feature.medical_data.domain.data.MedicalData
import org.skender.intime.feature.medical_data.domain.data.MedicalDataInfo
import org.skender.intime.network.ApiInterface
import javax.inject.Inject

class MedicalDataRepository @Inject constructor(api: ApiInterface) {
    suspend fun onLoadData(): List<MedicalDataInfo> {
        //TODO prepare response from server and map data to return value
        return emptyList()
    }

    suspend fun onSaveData(data: MedicalData) {
        //TODO add api for save data. Prepare data for save to somewhere
    }
}