package com.example.swiftyproteins.di

import com.example.swiftyproteins.presentation.viewmodels.ProteinViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val ProteinModule = module {

    viewModel {  ->
        ProteinViewModel(get())
    }
}