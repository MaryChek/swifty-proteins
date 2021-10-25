package com.example.swiftyproteins.presentation.viewmodels

import com.example.swiftyproteins.data.model.AtomsInfo
import com.example.swiftyproteins.data.model.ErrorType
import com.example.swiftyproteins.domain.interactor.ProteinInteractor
import com.example.swiftyproteins.presentation.logD
import com.example.swiftyproteins.presentation.logE
import com.example.swiftyproteins.presentation.mapper.ProteinMapper
import com.example.swiftyproteins.presentation.models.Protein
import com.example.swiftyproteins.presentation.models.ProteinError
import com.example.swiftyproteins.presentation.models.State
import com.example.swiftyproteins.presentation.navigation.FromProtein
import com.example.swiftyproteins.presentation.viewmodels.base.BaseScreenStateViewModel
import com.google.ar.sceneform.Node
import java.lang.IllegalStateException

class ProteinViewModel(
    private val interactor: ProteinInteractor,
    private val mapper: ProteinMapper
) : BaseScreenStateViewModel<FromProtein, Protein>(Protein()) {

    private var proteinName: String? = null

    private fun updateModel(atoms: Protein) {
        model = atoms
        handleModel()
    }

    fun onViewCreated(proteinName: String) {
        this.proteinName = proteinName
        getProteins(proteinName)
    }

    private fun getProteins(proteinName: String) {
        handleState(State.Loading)
        interactor.getProteinByName(proteinName,
            onSuccess = { ligand ->
                val atoms = mapper.map(ligand)
                handleState(State.Success)
                updateModel(atoms)
            },
            onError = { errorType ->
                handleState(State.Success)
                onError(errorType)
            })
    }

    private fun onError(errorType: ErrorType) {
        when (errorType) {
            ErrorType.NotFound -> handleAction(
                FromProtein.Command.ShowNotFoundErrorDialog(
                    ProteinError.ProteinNotFound
                )
            )
            ErrorType.Network -> handleAction(
                FromProtein.Command.ShowNetworkErrorDialog(
                    ProteinError.NetworkError
                )
            )
        }
    }

    fun onNotFoundDialogCancelable() =
        onBackClick()

    fun onNetworkErrorDialogCancelable() =
        onBackClick()

    fun onNetworkErrorDialogRetryClick() {
        proteinName?.let { name ->
            getProteins(name)
        } ?: logE("missing protein name", IllegalStateException())
    }

    fun onNodeTouch(node: Node) {
        node.name?.let { name ->
            interactor.getAtomInfoByBaseName(mapper.mapAtomName(name))?.let { atomInfo ->
                showAtomInfo(atomInfo)
            } ?: "information about $name not found"
        }
    }

    private fun showAtomInfo(atomInfo: AtomsInfo.AtomInfo) {
        logD(atomInfo.toString())
    }

    fun onBackClick() =
        handleAction(FromProtein.Navigate.Back)
}