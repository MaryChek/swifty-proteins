package com.example.swiftyproteins.presentation.navigation

import com.example.swiftyproteins.presentation.models.ProteinError

sealed class FromLogin: Action {
    sealed class Navigate: FromLogin() {
        object ProteinList: Navigate()
    }

    sealed class Command: FromLogin() {
        object ShowBiometricAuthDialog : Command()

        class ShowAuthErrorDialog(val error: ProteinError.AuthError): Command()
    }
}