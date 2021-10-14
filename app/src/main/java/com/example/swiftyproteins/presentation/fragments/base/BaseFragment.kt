package com.example.swiftyproteins.presentation.fragments.base

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.swiftyproteins.presentation.navigation.Action
import com.example.swiftyproteins.presentation.viewmodels.base.BaseViewModel

abstract class BaseFragment<ActionType: Action> : Fragment() {
    // TODO нужно раскомментить, когда появится Koin
    //    protected val router by inject<Router>()

    open val viewModel: BaseViewModel<ActionType>? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObserve()
    }

    protected open fun setupObserve() {
        viewModel?.actionUpdated?.observe(viewLifecycleOwner, Observer(this::handleAction))
    }

    abstract fun handleAction(action: ActionType)
}