package com.example.swiftyproteins.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.swiftyproteins.domain.interactor.ProteinInteractor
import com.example.swiftyproteins.presentation.mapper.ProteinMapper
import com.example.swiftyproteins.presentation.viewmodels.LoginViewModel
import com.example.swiftyproteins.presentation.viewmodels.ProteinListViewModel
import com.example.swiftyproteins.presentation.viewmodels.ProteinViewModel
import java.lang.IllegalArgumentException

class PokemonViewModelFactory(
    private val interactor: ProteinInteractor,
): ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        when (modelClass) {
            ProteinListViewModel::class.java -> ProteinListViewModel(interactor) as T
            ProteinViewModel::class.java -> ProteinViewModel(interactor, ProteinMapper()) as T
            LoginViewModel::class.java -> LoginViewModel() as T
            else -> throw IllegalArgumentException("Factory cannot make ViewModel of type ${modelClass.simpleName}")
        }
}