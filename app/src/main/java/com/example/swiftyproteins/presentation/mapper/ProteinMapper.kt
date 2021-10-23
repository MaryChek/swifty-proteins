package com.example.swiftyproteins.presentation.mapper

import android.graphics.Color
import com.example.swiftyproteins.presentation.models.Atom
import com.example.swiftyproteins.presentation.models.AtomConnection
import com.example.swiftyproteins.presentation.models.Protein
import com.example.swiftyproteins.presentation.toVec3
import com.google.ar.sceneform.math.Vector3
import com.example.swiftyproteins.domain.models.Atom as DomainAtom

class ProteinMapper {
    fun map(ligand: List<DomainAtom>, isAtomHVisible: Boolean = false): Protein {
        val atoms = mutableListOf<Atom>()
        val atomsConnection = mutableListOf<AtomConnection>()
        ligand.mapIndexed { index, atom ->
            if (isAtomHVisible || (!isAtomHVisible && atom.base != DomainAtom.BaseAtom.H)) {
                val coordinateAtom: Vector3 = atom.coordinate.toVec3()

                atoms.add(
                    Atom(
                        atom.name,
                        coordinateAtom,
                        parseColor(atom.base)
                    )
                )
                val atomConnections: List<AtomConnection> = getAtomConnections(
                    coordinateAtom, atom.connectList, index, ligand, isAtomHVisible
                )
                if (atomConnections.isNotEmpty()) {
                    atomsConnection.addAll(atomConnections)
                }
            }
        }
        return Protein(atoms, atomsConnection)
    }

    private fun parseColor(baseAtom: DomainAtom.BaseAtom): Int =
        when (baseAtom) {
            DomainAtom.BaseAtom.C -> Color.GRAY
            DomainAtom.BaseAtom.O -> Color.RED
            DomainAtom.BaseAtom.H -> Color.WHITE
            DomainAtom.BaseAtom.N -> Color.BLUE
            DomainAtom.BaseAtom.P -> Color.YELLOW
            DomainAtom.BaseAtom.F -> Color.GREEN
            DomainAtom.BaseAtom.OTHER -> Color.MAGENTA
        }

    private fun getAtomConnections(
        coordinateTop: Vector3,
        connections: List<String>,
        currentAtomIndex: Int,
        ligand: List<DomainAtom>,
        isAtomHVisible: Boolean
    ): List<AtomConnection> =
        connections.mapNotNull { atomId ->
            if (atomId.toInt() > currentAtomIndex.minus((1))) {
                ligand.find { secondAtom ->
                    isItNeededSecondAtomInConnection(secondAtom, atomId, isAtomHVisible)
                }?.coordinate?.toVec3()?.let { coordinateBottom ->
                    AtomConnection(
                        coordinateTop,
                        coordinateBottom,
                        Color.GRAY
                    )
                }
            } else {
                null
            }
        }

    private fun isItNeededSecondAtomInConnection(
        secondAtom: DomainAtom,
        firstAtomId: String,
        isAtomHVisible: Boolean
    ): Boolean =
        secondAtom.id == firstAtomId &&
                (isAtomHVisible || (!isAtomHVisible && secondAtom.base != DomainAtom.BaseAtom.H))
}