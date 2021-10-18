package com.example.swiftyproteins.domain.interactor

import com.example.swiftyproteins.data.repository.ProteinsRepository
import com.example.swiftyproteins.domain.models.Atom

class ProteinInteractor(private val repository: ProteinsRepository) {
    fun getProteinByName(name: String, onSuccess: (List<Atom>) -> Unit) =
        repository.getAtomByName(name, onSuccess)
}