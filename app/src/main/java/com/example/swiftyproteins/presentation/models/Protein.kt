package com.example.swiftyproteins.presentation.models

class Protein(
    val atoms: List<Atom> = emptyList(),
    val atomConnections: List<AtomConnection> = emptyList()
)