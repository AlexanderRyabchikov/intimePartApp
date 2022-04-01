package org.skender.intime.base

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import org.skender.intime.App
import org.skender.intime.base.extensions.convertFromActivity
import org.skender.intime.di.AppComponent
import org.skender.intime.di.HasComponent

abstract class BaseFragment<
        SpecificBinding : ViewBinding, VM : BaseViewModel<out ViewState, out ContentViewState>,
        State : ViewState>() : Fragment(),
    ViewStateDispatcher<State> {

    abstract val viewModel: VM

    private var _binding: SpecificBinding? = null

    val binding get() = _binding!!

    abstract fun bindView(view: View): SpecificBinding

    private fun viewCreated(
        view: View
    ) {
        _binding = bindView(view)
        initViews()
    }

    fun destroyView() {
        _binding = null
    }

    @Suppress("OptionalUnit")
    open fun initViews() = Unit

    @get: LayoutRes
    protected abstract val  layoutRes: Int

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(layoutRes, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        injectDependencies()
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getAppComponent().inject(this)
        viewCreated(view)
    }

    override fun onDestroyView() {
        destroyView()
        super.onDestroyView()
    }

    private fun getAppComponent(): AppComponent =
        (activity?.application as App).getAppComponent()

    protected fun <C> getComponent(componentType: Class<C>): C? {
        return componentType.cast(convertFromActivity<HasComponent<C>>().getComponent())
    }

    protected open fun injectDependencies() {
        Log.d(javaClass.name, "no base implementation for dependencies injection")
    }

}