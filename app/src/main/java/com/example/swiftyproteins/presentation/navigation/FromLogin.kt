package com.example.swiftyproteins.presentation.navigation

sealed class FromLogin: Action {
    sealed class Navigate: FromLogin() {
        object ProteinList: Navigate()
    }

//    sealed class Command: FromLogin() {
//
//    }
}