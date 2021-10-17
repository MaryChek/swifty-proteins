package com.example.swiftyproteins.presentation

import android.app.Application
import com.example.swiftyproteins.data.api.ClientCreator
import com.example.swiftyproteins.data.api.ProteinApiTalker
import com.example.swiftyproteins.data.repository.ProteinsRepository
import okhttp3.OkHttpClient

class App: Application() {
    lateinit var repository: ProteinsRepository

    override fun onCreate() {
        super.onCreate()

        //TODO move to Koin
        val client = ClientCreator().createClient(BASE_URL, OkHttpClient.Builder())
        val apiTalker = ProteinApiTalker(client)
        repository = ProteinsRepository(apiTalker)
    }

    companion object {
        private const val BASE_URL = "https://files.rcsb.org/"
    }
}