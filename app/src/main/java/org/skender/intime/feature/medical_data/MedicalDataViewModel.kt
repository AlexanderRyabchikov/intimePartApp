package org.skender.intime.feature.medical_data

import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.launch
import org.skender.intime.base.BaseViewModel
import org.skender.intime.feature.medical_data.domain.MedicalDataInteractor
import org.skender.intime.feature.medical_data.domain.data.MedicalData
import org.skender.intime.feature.medical_data.domain.data.TypeMedicalData
import timber.log.Timber
import java.lang.Exception

class MedicalDataViewModel @AssistedInject constructor(
    private val interactor: MedicalDataInteractor,
    @Assisted private val navController: NavController
) : BaseViewModel <MedicalDataViewState, MedicalDataViewState.Content>(
    initialStateAndDefaultContentState = { MedicalDataViewState.Content() to MedicalDataViewState.Content() }
) {

    private var saveDataList = mutableListOf<MedicalData>()

    init {
        onRefresh()
    }

    private fun onRefresh() {

        viewModelScope.launch {
            try {
                updateState(MedicalDataViewState.Loading)
                interactor
                    .onLoadData()
                    .run {
                        if (isNotEmpty()) {
                            var content = MedicalDataViewState.Content()
                            forEach { item ->
                                when(item.type) {
                                    TypeMedicalData.HEART_RATE -> {
                                        content = content.copy(
                                            heartRate = item
                                        )
                                    }

                                    TypeMedicalData.SLEEP_TIME -> {
                                        content = content.copy(
                                            sleepTime = item
                                        )
                                    }
                                    TypeMedicalData.BLOOD_PRESSURE -> {
                                        content = content.copy(
                                            bloodPressure = item
                                        )
                                    }
                                    TypeMedicalData.BMI -> {
                                        content = content.copy(
                                            bmi = item
                                        )
                                    }
                                    TypeMedicalData.MAIN_RISK_LEVEL -> {
                                        content = content.copy(
                                            mainRiskLevel = item
                                        )
                                    }
                                    TypeMedicalData.WEIGHT -> {
                                        content = content.copy(
                                            weight = item
                                        )
                                    }
                                    TypeMedicalData.CHOLESTEROL -> {
                                        content = content.copy(
                                            cholesterol = item
                                        )
                                    }
                                    TypeMedicalData.GLUCOSE -> {
                                        content = content.copy(
                                            glucose = item
                                        )
                                    }
                                }
                            }
                            updateState(content)
                        }
                    }
            } catch (e: Exception) {
                Timber.e(e)
                updateState(MedicalDataViewState.Error(e))
            }
        }
    }

    fun onBack() {
        navController.navigateUp()
    }

    fun onClickSave() {
        //TODO save data
    }

    fun onInputData(name: String, value: String) {
        if (saveDataList.none { it.name == name }) {
            saveDataList.add(
                MedicalData(
                    saveDataList.size,
                    name = name,
                    value = value
                )
            )
        } else {
            var item = saveDataList.first { it.name == name }
            val index = saveDataList.indexOf(item)
            item = item.copy(
                value = value
            )
            saveDataList[index] = item
        }
    }


    @dagger.assisted.AssistedFactory
    interface AssistedFactory {
        fun create(navController: NavController): MedicalDataViewModel
    }

}