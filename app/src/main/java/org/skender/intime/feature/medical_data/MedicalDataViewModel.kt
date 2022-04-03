package org.skender.intime.feature.medical_data

import androidx.navigation.NavController
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import org.skender.intime.base.BaseViewModel
import org.skender.intime.feature.medical_data.domain.MedicalData

class MedicalDataViewModel @AssistedInject constructor(
    @Assisted private val navController: NavController
)
    : BaseViewModel <MedicalDataViewState, MedicalDataViewState.Content>(
    initialStateAndDefaultContentState = { MedicalDataViewState.Content() to MedicalDataViewState.Content() }
) {

    private var saveDataList = mutableListOf<MedicalData>()

    fun onBack() {
        navController.navigateUp()
    }

    fun onClickSave() {
        val list = saveDataList
        navController.navigateUp()
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