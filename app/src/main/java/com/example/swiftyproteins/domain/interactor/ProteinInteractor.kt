package com.example.swiftyproteins.domain.interactor

import androidx.annotation.RawRes
import com.example.swiftyproteins.data.repository.ProteinsRepository
import com.example.swiftyproteins.domain.mapper.ProteinMapper
import com.example.swiftyproteins.domain.models.Atom

//4fw, 15p, b12, dw2, lz0, pc1, pve, rmd // not open hec // unl, unx - file not found 404
class ProteinInteractor(
    private val repository: ProteinsRepository,
    private val fileInteractor: FileInteractor,
    private val mapper: ProteinMapper,
) {
    private var proteinsFilter: String = ""

    fun getProteinByName(name: String, onSuccess: (List<Atom>) -> Unit, onError: () -> Unit) =
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

    fun getProteins(@RawRes fileResId: Int): List<String> =
        fileInteractor.getFileListString(fileResId).filter { line ->
            if (proteinsFilter.isNotBlank()) {
                line.contains(proteinsFilter, true)
            } else {
                true
            }
        }
}