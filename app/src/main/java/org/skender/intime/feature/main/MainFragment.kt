package org.skender.intime.feature.main

import android.view.View
import androidx.navigation.fragment.findNavController
import org.skender.intime.R
import org.skender.intime.base.BaseFragment
import org.skender.intime.base.assistedViewModel
import org.skender.intime.databinding.MainFragmentBinding
import org.skender.intime.di.MainComponent
import javax.inject.Inject

class MainFragment: BaseFragment<
        MainFragmentBinding,
        MainFragmentViewModel,
        MainFragmentStateView>()
{
    @Inject
    lateinit var mainFragmentViewModelFactory: MainFragmentViewModel.AssistedFactory

    override val viewModel: MainFragmentViewModel by assistedViewModel {
        mainFragmentViewModelFactory.create(findNavController())
    }

    override val layoutRes: Int = R.layout.main_fragment

    override fun injectDependencies() {
        super.injectDependencies()
        getComponent(MainComponent::class.java)?.inject(this)
    }

    override fun bindView(view: View) = MainFragmentBinding.bind(view)

    override fun initViews() = with(binding) {
        openMedicalData.setOnClickListener {
            viewModel.onClickButton()
        }
    }

    override fun renderState(state: MainFragmentStateView) {}
}