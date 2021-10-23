package com.example.swiftyproteins.presentation.fragments.base

import androidx.lifecycle.Observer
import com.example.swiftyproteins.presentation.navigation.Action
import com.example.swiftyproteins.presentation.viewmodels.base.BaseScreenStateViewModel

abstract class BaseScreenStateFragment<
        ActionType : Action, Model : Any, ViewModel : BaseScreenStateViewModel<ActionType, Model>>
    : BaseFragment<ActionType, ViewModel>() {

    override fun setupObserve() {
        super.setupObserve()
        viewModel?.modelUpdated?.observe(viewLifecycleOwner, Observer(::handleModel))
    }

    abstract fun handleModel(model: Model)
}