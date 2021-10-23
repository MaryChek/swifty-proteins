package com.example.swiftyproteins.data.mapper

import com.example.swiftyproteins.data.model.Atom
import com.example.swiftyproteins.data.model.Connection
import com.example.swiftyproteins.domain.models.Atom as DomainAtom
import com.example.swiftyproteins.domain.models.Atom.Coordinate
import com.example.swiftyproteins.presentation.splitOnlyWords
import okhttp3.ResponseBody
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader

//TODO in data rename to ligand
class ProteinMapper {
    fun map(resource: ResponseBody): List<DomainAtom> {
        val inStream: InputStream = resource.byteStream()
        val reader = BufferedReader(InputStreamReader(inStream))
        var line: String
        val atoms = mutableListOf<Atom>()
        val connects = mutableListOf<Connection>()
        while (reader.readLine().also { line = it } != null) {
            if (line.contains(END)) {
                break
            }
            val splitLine: List<String> = line.splitOnlyWords()
            val parameterName = splitLine[PARAMETER]
            val id = splitLine[ID]
            if (parameterName == ATOM) {
                atoms.add(parseAtom(id, splitLine))
            } else if (parameterName == CONNECT) {
                connects.add(parseConnect(id, splitLine))
            }
        }
        resource.close()
        return map(atoms, connects)
    }

    private fun parseAtom(id: String, parameters: List<String>): Atom =
        Atom(
            id = id,
            name = parameters[ATOM_NAME],
            x = parameters[X],
            y = parameters[Y],
            z = parameters[Z],
            baseAtomName = parameters[BASE_ATOM_NAME]
        )

    private fun parseConnect(id: String, parameters: List<String>): Connection {
        val atomIdsConnection = mutableListOf<String>()
        parameters.forEachIndexed { index, parameter ->
            if (index >= FIRST_CONNECT_ID) {
                atomIdsConnection.add(parameter)
            }
        }
        return Connection(id, atomIdsConnection)
    }

    private fun map(atoms: List<Atom>, connections: List<Connection>): List<DomainAtom> =
        atoms.map { atom ->
            DomainAtom(
                id = atom.id,
                name = atom.name,
                coordinate = Coordinate(atom.x.toFloat(), atom.y.toFloat(), atom.z.toFloat()),
                base = parseBaseAtom(atom.baseAtomName),
                connectList = connections.find { connection ->
                    connection.id == atom.id
                }?.atomIdList ?: listOf()
            )
        }

    private fun parseBaseAtom(baseAtomName: String): DomainAtom.BaseAtom =
        when (baseAtomName) {
            ATOM_C -> DomainAtom.BaseAtom.C
            ATOM_O -> DomainAtom.BaseAtom.O
            ATOM_H -> DomainAtom.BaseAtom.H
            ATOM_P -> DomainAtom.BaseAtom.P
            ATOM_N -> DomainAtom.BaseAtom.N
            ATOM_F -> DomainAtom.BaseAtom.F
            else -> DomainAtom.BaseAtom.OTHER
        }

    companion object {
        private const val PARAMETER = 0
        private const val ID = 1
        private const val ATOM_NAME = 2
        private const val X = 6
        private const val Y = 7
        private const val Z = 8
        private const val BASE_ATOM_NAME = 11
        private const val ATOM_C = "C"
        private const val ATOM_O = "O"
        private const val ATOM_H = "H"
        private const val ATOM_P = "P"
        private const val ATOM_N = "N"
        private const val ATOM_F = "F"
        private const val FIRST_CONNECT_ID = 2
        private const val ATOM = "ATOM"
        private const val CONNECT = "CONECT"
        private const val END = "END"
    }
}