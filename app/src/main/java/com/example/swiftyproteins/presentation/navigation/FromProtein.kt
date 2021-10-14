package com.example.swiftyproteins.presentation.navigation

sealed class FromProtein : Action {
    sealed class Navigate: FromProtein() {
        object Back: Navigate()
    }

//    sealed class Command: FromProtein() {
//
//    }
}