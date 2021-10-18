package com.example.swiftyproteins.presentation

import android.app.Application
import com.example.swiftyproteins.data.api.ClientCreator
import com.example.swiftyproteins.data.api.ProteinApiTalker
import com.example.swiftyproteins.data.mapper.ProteinMapper
import com.example.swiftyproteins.data.repository.ProteinsRepository
import com.example.swiftyproteins.domain.interactor.ProteinInteractor
import okhttp3.OkHttpClient

class App: Application() {
    lateinit var interactor: ProteinInteractor

    override fun onCreate() {
        super.onCreate()

        //TODO move to Koin
        val client = ClientCreator().createClient(BASE_URL, OkHttpClient.Builder())
        val apiTalker = ProteinApiTalker(client)
        val repository = ProteinsRepository(apiTalker, ProteinMapper())
        interactor = ProteinInteractor(repository)
    }

    companion object {
        private const val BASE_URL = "https://files.rcsb.org/"
    }
}