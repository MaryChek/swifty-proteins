package com.example.swiftyproteins.domain.interactor

import com.example.swiftyproteins.data.model.AtomsInfo
import com.example.swiftyproteins.data.model.ErrorType
import com.example.swiftyproteins.data.repository.ProteinsRepository
import com.example.swiftyproteins.domain.mapper.ProteinMapper
import com.example.swiftyproteins.domain.models.Atom
import com.google.gson.Gson

//4fw, 15p, b12, dw2, lz0, pc1, pve, rmd // not open hec // unl, unx - file not found 404
class ProteinInteractor(
    private val repository: ProteinsRepository,
    private val fileInteractor: FileInteractor,
    private val mapper: ProteinMapper,
) {
    private var proteinsFilter: String = ""

    fun getProteinByName(name: String, onSuccess: (List<Atom>) -> Unit, onError: (ErrorType) -> Unit) =
        repository.getAtomByName(
            name = name,
            onSuccess = { protein ->
                onSuccess(mapper.map(protein))
            },
            onError = onError
        )

    fun setFilterForProteins(filter: String) {
        proteinsFilter = filter
    }

    fun getProteins(): List<String> =
        fileInteractor.getProteinsListString().filter { line ->
            if (proteinsFilter.isNotBlank()) {
                line.contains(proteinsFilter, true)
            } else {
                true
            }
        }

    fun getAtomInfoByBaseName(name: String): AtomsInfo.AtomInfo? {
        val infoJsonString = fileInteractor.readAtomsInfo()
        val atomsInfo = Gson().fromJson(infoJsonString, AtomsInfo::class.java)
        return atomsInfo.elements.find { atomInfo ->
            atomInfo.symbol == name
        }
    }
}