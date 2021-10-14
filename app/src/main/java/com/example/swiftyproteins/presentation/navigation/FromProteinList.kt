package com.example.swiftyproteins.presentation.navigation

sealed class FromProteinList: Action {
    sealed class Navigate: FromProteinList() {
        class Protein(val proteinName: String): Navigate()
    }
}