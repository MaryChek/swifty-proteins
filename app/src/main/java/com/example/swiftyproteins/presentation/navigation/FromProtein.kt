package com.example.swiftyproteins.presentation.navigation

import com.example.swiftyproteins.presentation.models.ProteinError

sealed class FromProtein : Action {
    sealed class Navigate : FromProtein() {
        object Back : Navigate()
    }

    sealed class Command : FromProtein() {
        class ShowNetworkErrorDialog(val error: ProteinError.NetworkError) : Command()

        class ShowNotFoundErrorDialog(val error: ProteinError.ProteinNotFound) : Command()
    }
}