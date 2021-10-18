package com.example.swiftyproteins.data.repository

import com.example.swiftyproteins.data.api.ProteinApiTalker
import com.example.swiftyproteins.data.mapper.ProteinMapper
import com.example.swiftyproteins.domain.models.Atom

class ProteinsRepository(
    private val apiTalker: ProteinApiTalker,
    private val mapper: ProteinMapper
) {
    fun getAtomByName(name: String, onSuccess: (List<Atom>) -> Unit) {
        apiTalker.getProteinByName(name) { body ->
            onSuccess(mapper.map(body))
        }
    }
}