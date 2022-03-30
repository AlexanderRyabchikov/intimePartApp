package org.skender.intime.feature.medical_data

import androidx.navigation.NavController
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import org.skender.intime.base.BaseViewModel

class MedicalDataViewModel @AssistedInject constructor(
    @Assisted private val navController: NavController
)
    : BaseViewModel <MedicalDataViewState, MedicalDataViewState.Content>(
    initialStateAndDefaultContentState = { MedicalDataViewState.Content() to MedicalDataViewState.Content() }
) {

    fun onBack() {
        navController.navigateUp()
    }


    @dagger.assisted.AssistedFactory
    interface AssistedFactory {
        fun create(navController: NavController): MedicalDataViewModel
    }

}