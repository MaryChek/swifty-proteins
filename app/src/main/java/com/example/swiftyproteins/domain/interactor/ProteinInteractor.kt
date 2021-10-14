package com.example.swiftyproteins.domain.interactor

import com.example.swiftyproteins.domain.models.Atom

class ProteinInteractor {
    fun getAtomByName(name: String): Atom {
        return Atom("", "", Atom.Coordinate(0.0, 0.0, 0.0), false, listOf())
    }
}