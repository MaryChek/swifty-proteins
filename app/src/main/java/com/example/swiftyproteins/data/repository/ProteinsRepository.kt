package com.example.swiftyproteins.data.repository

import com.example.swiftyproteins.data.api.ProteinApiTalker
import okhttp3.ResponseBody

class ProteinsRepository(
    private val apiTalker: ProteinApiTalker,
) {
    fun getAtomByName(name: String, onSuccess: (ResponseBody) -> Unit) {
        apiTalker.getProteinByName(name) { body ->
            onSuccess(body)
        }
    }
}