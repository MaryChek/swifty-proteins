package com.example.swiftyproteins.presentation.viewmodels

import android.hardware.biometrics.BiometricPrompt
import androidx.lifecycle.viewModelScope
import com.example.swiftyproteins.presentation.models.ProteinError
import com.example.swiftyproteins.presentation.navigation.FromLogin
import com.example.swiftyproteins.presentation.viewmodels.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class LoginViewModel : BaseViewModel<FromLogin>() {
    private var isLoginEnable: Boolean = true

    fun onAuthByFingerClick(isDeviceLocked: Boolean) {
        if (isDeviceLocked) {
            auth()
        } else {
            handleAction(FromLogin.Command.ShowDialogForSetPassLock)
        }
    }

    fun onFreeAuthClick() {
        handleAction(FromLogin.Navigate.ProteinList)
    }

    private fun auth() {
        if (isLoginEnable) {
            handleAction(FromLogin.Command.ShowBiometricAuthDialog)
        } else {
            handleAction(FromLogin.Command.ShowToast("Too many attempts, please try later"))
        }
    }

    fun onAuthSuccess() =
        handleAction(FromLogin.Navigate.ProteinList)

    fun onAuthError(errorCode: Int) {
        when (errorCode) {
            BiometricPrompt.BIOMETRIC_ERROR_LOCKOUT -> {
                isLoginEnable = false
                viewModelScope.launch(Dispatchers.Main) {
                    delay(MS_IN_MINUTE)
                    isLoginEnable = true
                }
            }
            BiometricPrompt.BIOMETRIC_ERROR_LOCKOUT_PERMANENT -> {
                handleAction(FromLogin.Command.ShowToast("Too many attempts, scanner disable"))
                viewModelScope.launch(Dispatchers.Main) {
                    delay(10000)
                    handleAction(FromLogin.Navigate.ProteinList)
                }
            }
            BiometricPrompt.BIOMETRIC_ERROR_HW_UNAVAILABLE -> {
                handleAction(FromLogin.Command.ShowToast("Fingerprint authentication on this device is unfortunately not available"))
                handleAction(FromLogin.Command.HideFingerprintButton)
            }
            else ->
                handleAction(FromLogin.Command.ShowAuthErrorDialog(ProteinError.AuthError))
        }
    }

    fun onAuthErrorDialogRetryClick() =
        auth()

    fun onSetupPassLockPositiveClick() {
        handleAction(FromLogin.Command.SetupPassLock)
    }

    companion object {
        private const val MS_IN_MINUTE = 30000L
    }
}