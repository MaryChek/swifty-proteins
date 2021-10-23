package com.example.swiftyproteins.presentation.viewmodels.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.swiftyproteins.presentation.navigation.Action
import com.example.swiftyproteins.presentation.view.controller.SingleEvent

abstract class BaseViewModel<ActionType: Action>: ViewModel() {

    private val action: MutableLiveData<ActionType> = SingleEvent()
//    protected var model = initModel
//
//    val modelUpdated = MutableLiveData<Model>()
    val actionUpdated: LiveData<ActionType> = action

    protected fun handleNavigate(destination: ActionType) {
        action.value = destination
    }

//    protected fun updateMode() {
//        modelUpdated.value = model
//    }
}