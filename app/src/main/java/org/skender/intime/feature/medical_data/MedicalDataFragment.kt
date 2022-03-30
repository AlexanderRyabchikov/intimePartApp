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
        testText.setOnClickListener {
            viewModel.onBack()
        }
    }

    override fun renderState(state: MedicalDataViewState) {

    }
}