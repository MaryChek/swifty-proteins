package com.example.swiftyproteins.domain.interactor

import com.example.swiftyproteins.data.repository.ProteinsRepository
import com.example.swiftyproteins.domain.models.Atom

class ProteinInteractor(private val repository: ProteinsRepository) {
    fun getAtomByName(name: String): Atom {
        return
    }
}