package org.skender.intime.feature.medical_data

import android.view.View
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import org.skender.intime.R
import org.skender.intime.base.BaseFragment
import org.skender.intime.base.assistedViewModel
import org.skender.intime.databinding.MedicalDataFragmentBinding
import org.skender.intime.di.MainComponent
import org.skender.intime.feature.medical_data.domain.data.MedicalDataInfo
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
                hideContent()
                hideError()
                showModalProgress()
            }

            is MedicalDataViewState.Content -> {
                hideModalProgress()
                hideError()
                showContent()
                renderData(state)
            }

            is MedicalDataViewState.Error -> {
                showError()
                hideModalProgress()
                hideContent()
            }
        }
    }

    private fun renderData(render: MedicalDataViewState.Content) {
        renderHeartRate(render.heartRate)
        renderSleepTime(render.sleepTime)
        renderBloodPressure(render.bloodPressure)
        renderBMI(render.bmi)
        renderMainRiskLevel(render.mainRiskLevel)
        renderWeight(render.weight)
        renderCholesterol(render.cholesterol)
        renderGlucose(render.glucose)
    }

    private fun renderHeartRate(data: MedicalDataInfo?) {
        with(binding) {
            data?.let { info ->
                heartRate.text = info.value
                heartRate.menuItems = info.listTypeData
                heartRate.suffix = info.unit
            }
        }
    }

    private fun renderSleepTime(data: MedicalDataInfo?) {
        with(binding) {
            data?.let { info ->
                sleepTime.text = info.value
                sleepTime.menuItems = info.listTypeData
                sleepTime.suffix = info.unit
            }
        }
    }

    private fun renderBloodPressure(data: MedicalDataInfo?) {
        with(binding) {
            data?.let { info ->
                bloodPressure.text = info.value
                bloodPressure.menuItems = info.listTypeData
                bloodPressure.suffix = info.unit
            }
        }
    }

    private fun renderBMI(data: MedicalDataInfo?) {
        with(binding) {
            data?.let { info ->
                bodyMassIndex.text = info.value
                bodyMassIndex.menuItems = info.listTypeData
                bodyMassIndex.suffix = info.unit
            }
        }
    }

    private fun renderMainRiskLevel(data: MedicalDataInfo?) {
        with(binding) {
            data?.let { info ->
                mainRiskLevel.text = info.value
                mainRiskLevel.menuItems = info.listTypeData
                mainRiskLevel.suffix = info.unit
            }
        }
    }

    private fun renderWeight(data: MedicalDataInfo?) {
        with(binding) {
            data?.let { info ->
                weight.text = info.value
                weight.menuItems = info.listTypeData
                weight.suffix = info.unit
            }
        }
    }

    private fun renderCholesterol(data: MedicalDataInfo?) {
        with(binding) {
            data?.let { info ->
                cholesterol.text = info.value
                cholesterol.menuItems = info.listTypeData
                cholesterol.suffix = info.unit
            }
        }
    }

    private fun renderGlucose(data: MedicalDataInfo?) {
        with(binding) {
            data?.let { info ->
                glucose.text = info.value
                glucose.menuItems = info.listTypeData
                glucose.suffix = info.unit
            }
        }
    }

    private fun callbackEditText(name: String, value: String) {
        viewModel.onInputData(name, value)
    }

    private fun showContent() {
        binding.mainDataContainer.isVisible = true
    }

    private fun hideContent() {
        binding.mainDataContainer.isVisible = false
    }

    private fun showError() {
        binding.errorMessage.isVisible = true
    }

    private fun hideError() {
        binding.errorMessage.isVisible = false
    }
}