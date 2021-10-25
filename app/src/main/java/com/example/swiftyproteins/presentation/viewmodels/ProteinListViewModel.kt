package com.example.swiftyproteins.presentation.viewmodels

import com.example.swiftyproteins.R
import com.example.swiftyproteins.domain.interactor.ProteinInteractor
import com.example.swiftyproteins.presentation.navigation.FromProteinList
import com.example.swiftyproteins.presentation.viewmodels.base.BaseScreenStateViewModel

class ProteinListViewModel(private val interactor: ProteinInteractor) :
    BaseScreenStateViewModel<FromProteinList, List<String>>(emptyList()) {

    private fun updateModel(model: List<String>) {
        this.model = model
        handleModel()
    }

    fun onViewCreated() {
        getLigands()
    }

    private fun getLigands() {
        val proteinNames: List<String> = interactor.getProteins()
        updateModel(proteinNames)
    }

    fun onSearchTextChanged(text: String) {
        interactor.setFilterForProteins(text)
        getLigands()
    }

    fun onProteinClick(proteinName: String) =
        handleAction(FromProteinList.Navigate.Protein(proteinName))

    fun onBackPressed() {

    }
}