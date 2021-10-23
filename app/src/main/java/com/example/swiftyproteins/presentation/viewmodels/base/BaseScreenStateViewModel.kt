package com.example.swiftyproteins.presentation.viewmodels.base

import androidx.lifecycle.MutableLiveData
import com.example.swiftyproteins.presentation.navigation.Action

abstract class BaseScreenStateViewModel<ActionType : Action, Model : Any>(initModel: Model)
    : BaseViewModel<ActionType>() {

    protected var model: Model = initModel

    val modelUpdated = MutableLiveData<Model>()

    protected fun handleModel() {
        modelUpdated.value = model
    }
}