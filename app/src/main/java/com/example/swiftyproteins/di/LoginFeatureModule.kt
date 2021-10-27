package com.example.swiftyproteins.di

import com.example.swiftyproteins.presentation.viewmodels.LoginViewModel
import org.koin.androidx.viewmodel.dsl.viewModel

import org.koin.dsl.module

val LoginModule = module {

    viewModel {  ->
        LoginViewModel(get())
    }
}