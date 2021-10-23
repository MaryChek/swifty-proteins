package com.example.swiftyproteins.domain.interactor

import androidx.annotation.RawRes
import com.example.swiftyproteins.data.repository.ProteinsRepository
import com.example.swiftyproteins.domain.models.Atom

class ProteinInteractor(
    private val repository: ProteinsRepository,
    private val fileInteractor: FileInteractor,
) {
    private var proteinsFilter: String = ""

    fun getProteinByName(name: String, onSuccess: (List<Atom>) -> Unit) =
        repository.getAtomByName(name, onSuccess)

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