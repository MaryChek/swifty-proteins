package com.example.swiftyproteins.presentation

import android.app.Application
import com.example.swiftyproteins.data.api.ClientCreator
import com.example.swiftyproteins.data.api.ProteinApiTalker
import com.example.swiftyproteins.data.mapper.ProteinMapper
import com.example.swiftyproteins.data.repository.ProteinsRepository
import com.example.swiftyproteins.domain.interactor.FileInteractor
import com.example.swiftyproteins.domain.interactor.ProteinInteractor
import com.example.swiftyproteins.presentation.viewmodels.ProteinListViewModel
import okhttp3.OkHttpClient

class App: Application() {
//    lateinit var interactor: ProteinInteractor
    lateinit var viewModelFactory: PokemonViewModelFactory

    override fun onCreate() {
        super.onCreate()

        //TODO move to Koin
        val client = ClientCreator().createClient(BASE_URL, OkHttpClient.Builder())
        val apiTalker = ProteinApiTalker(client)
        val repository = ProteinsRepository(apiTalker, ProteinMapper())
        val fileInteractor = FileInteractor(applicationContext)
        val interactor = ProteinInteractor(repository, fileInteractor)
        viewModelFactory = PokemonViewModelFactory(interactor)
    }

    companion object {
        private const val BASE_URL = "https://files.rcsb.org/"
    }
}