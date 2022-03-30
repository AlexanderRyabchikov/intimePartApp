package org.skender.intime.feature.main

import androidx.navigation.NavController
import androidx.navigation.NavDirections
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import org.skender.intime.R
import org.skender.intime.base.BaseViewModel

class MainFragmentViewModel @AssistedInject constructor(
    @Assisted private val navController: NavController
):
    BaseViewModel<MainFragmentStateView, MainFragmentStateView.Content>(
        initialStateAndDefaultContentState = {
            MainFragmentStateView.Content() to MainFragmentStateView.Content()
        })
{
    fun onClickButton() {
        navController.navigate(R.id.action_mainFragment_to_medicalDataFragment)
    }

    @dagger.assisted.AssistedFactory
    interface AssistedFactory {
        fun create(navController: NavController): MainFragmentViewModel
    }
}