package com.example.swiftyproteins.presentation.fragments.base

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.swiftyproteins.presentation.App
import com.example.swiftyproteins.presentation.navigation.Action
import com.example.swiftyproteins.presentation.viewmodels.base.BaseViewModel

abstract class BaseFragment<ActionType : Action, ViewMode : BaseViewModel<ActionType>>
    : Fragment() {
    // TODO нужно раскомментить, когда появится Koin
    //    protected val router by inject<Router>()

    open var viewModel: ViewMode? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val app: App = (requireActivity().applicationContext as App)
        val viewModelFactory = app.viewModelFactory
        val viewModelProvider = ViewModelProvider(this, viewModelFactory)
        viewModel = viewModelProvider.get(getViewModelClass())
    }

    abstract fun getViewModelClass(): Class<ViewMode>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObserve()
    }

    protected open fun setupObserve() {
        viewModel?.apply {
            actionUpdated.observe(viewLifecycleOwner, Observer(::handleAction))
//            modelUpdated.observe(viewLifecycleOwner, Observer(::handleState))
        }
    }

    abstract fun handleAction(action: ActionType)
}