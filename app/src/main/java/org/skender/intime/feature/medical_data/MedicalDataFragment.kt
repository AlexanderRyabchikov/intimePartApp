package org.skender.intime.feature.medical_data

import android.view.View
import androidx.navigation.fragment.findNavController
import org.skender.intime.R
import org.skender.intime.base.BaseFragment
import org.skender.intime.base.assistedViewModel
import org.skender.intime.databinding.MedicalDataFragmentBinding
import org.skender.intime.di.MainComponent
import javax.inject.Inject

class MedicalDataFragment: BaseFragment<
        MedicalDataFragmentBinding,
        MedicalDataViewModel,
        MedicalDataViewState>() {

    override val layoutRes: Int = R.layout.medical_data_fragment

    @Inject
    lateinit var medicalDataFragmentViewModelFactory: MedicalDataViewModel.AssistedFactory

    override val viewModel: MedicalDataViewModel by assistedViewModel {
        medicalDataFragmentViewModelFactory.create(findNavController())
    }

    override fun injectDependencies() {
        super.injectDependencies()
        getComponent(MainComponent::class.java)?.inject(this)
    }

    override fun bindView(view: View) = MedicalDataFragmentBinding.bind(view)

    override fun initViews() = with(binding) {
        toolbar.setNavigationOnClickListener {
            viewModel.onBack()
        }

        saveData.setOnClickListener {
            viewModel.onClickSave()
        }

        heartRate.inputListener = ::callbackEditText
        sleepTime.inputListener = ::callbackEditText
        bloodPressure.inputListener = ::callbackEditText
        bodyMassIndex.inputListener = ::callbackEditText
        mainRiskLevel.inputListener = ::callbackEditText
        weight.inputListener = ::callbackEditText
        cholesterol.inputListener = ::callbackEditText
        glucose.inputListener = ::callbackEditText
    }

    override fun renderState(state: MedicalDataViewState) {
        when(state) {
            is MedicalDataViewState.Loading -> {

            }

            is MedicalDataViewState.Content -> {

            }

            is MedicalDataViewState.Error -> {

            }
        }
    }

    private fun callbackEditText(name: String, value: String) {
        viewModel.onInputData(name, value)
    }
}