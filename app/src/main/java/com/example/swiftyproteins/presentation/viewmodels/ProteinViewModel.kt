package com.example.swiftyproteins.presentation.viewmodels

import com.example.swiftyproteins.domain.interactor.ProteinInteractor
import com.example.swiftyproteins.presentation.logD
import com.example.swiftyproteins.presentation.mapper.ProteinMapper
import com.example.swiftyproteins.presentation.models.Protein
import com.example.swiftyproteins.presentation.navigation.FromProtein
import com.example.swiftyproteins.presentation.viewmodels.base.BaseScreenStateViewModel
import com.google.ar.sceneform.Node

class ProteinViewModel(
    private val interactor: ProteinInteractor,
    private val mapper: ProteinMapper
) : BaseScreenStateViewModel<FromProtein, Protein>(Protein()) {

    private fun updateModel(atoms: Protein) {
        model = atoms
        handleModel()
    }

    fun onViewCreated(proteinName: String) {
        interactor.getProteinByName(proteinName) { ligand ->
            val atoms = mapper.map(ligand)
            updateModel(atoms)
        }
    }

    fun onNodeTouch(node: Node) {
        node.name?.let {
            logD(it)
        }
    }
}