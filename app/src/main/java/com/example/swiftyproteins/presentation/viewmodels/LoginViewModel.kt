package com.example.swiftyproteins.presentation.viewmodels

import com.example.swiftyproteins.presentation.models.ProteinError
import com.example.swiftyproteins.presentation.navigation.FromLogin
import com.example.swiftyproteins.presentation.viewmodels.base.BaseViewModel

class LoginViewModel: BaseViewModel<FromLogin>() {

    fun onAuthClick() {
        handleAction(FromLogin.Command.ShowBiometricAuthDialog)
    }

    fun onAuthSuccess() =
        handleAction(FromLogin.Navigate.ProteinList)

    fun onAuthError() =
        handleAction(FromLogin.Command.ShowAuthErrorDialog(ProteinError.AuthError))

    fun onAuthErrorDialogRetryClick() =
        onAuthClick()
}