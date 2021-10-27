package com.example.swiftyproteins.di

import com.example.swiftyproteins.presentation.viewmodels.ProteinListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val ProteinListModule = module {

    viewModel {  ->
        ProteinListViewModel(get())
    }
}