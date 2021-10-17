package com.example.swiftyproteins.data.api

import com.example.swiftyproteins.presentation.logE
import okhttp3.ResponseBody

class ProteinApiTalker(
    private val client: ProteinApi
): BaseApiTalker() {

    fun getProteinByName(proteinName: String, onSuccess: (ResponseBody) -> Unit) {
        getResult(
            client.getProtein(proteinName),
            onSuccess =  { body ->
//                logD(body.string())
                onSuccess(body)
            },
            onError = { errorMessage ->
                logE(errorMessage)
            }
        )
    }
}