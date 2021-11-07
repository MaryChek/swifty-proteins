package com.example.swiftyproteins.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.an.biometric.BiometricManager
import com.example.swiftyproteins.presentation.fragments.base.BaseFragment
import com.example.swiftyproteins.presentation.navigation.FromLogin
import com.example.swiftyproteins.presentation.navigation.Screens
import com.example.swiftyproteins.presentation.viewmodels.LoginViewModel
import com.example.swiftyproteins.databinding.FragmentLoginBinding
import com.example.swiftyproteins.presentation.dialog.DialogCreator
import com.example.swiftyproteins.presentation.models.ProteinError
import com.example.swiftyproteins.presentation.view.LoginBiometricCallback
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginFragment : BaseFragment<FromLogin, LoginViewModel>() {

    private var binding: FragmentLoginBinding? = null
    override val viewModel: LoginViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.btnAuth?.setOnClickListener {
            viewModel.onAuthClick()
        }
    }

    override fun handleAction(action: FromLogin) {
        when (action) {
            is FromLogin.Navigate.ProteinList ->
                router.navigateTo(Screens.ProteinList)
            is FromLogin.Command.ShowBiometricAuthDialog ->
                showBiometricAuthDialog()
            is FromLogin.Command.ShowAuthErrorDialog ->
                showAuthErrorDialog(action.error)
        }
    }

    private fun showBiometricAuthDialog() {
        val biometricCallback = object : LoginBiometricCallback() {
            override fun onAuthenticationSuccessful() {
                super.onAuthenticationSuccessful()
                viewModel.onAuthSuccess()
            }

            override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                super.onAuthenticationError(errorCode, errString)
                viewModel.onAuthError()
            }
        }
        BiometricManager.BiometricBuilder(activity)
            .setTitle("Add a title")
            .setSubtitle("Add a subtitle")
            .setDescription("Add a description")
            .setNegativeButtonText("Add a cancel button")
            .build()
            .authenticate(biometricCallback)
    }

    private fun showAuthErrorDialog(error: ProteinError.AuthError) {
        DialogCreator().showAuthErrorDialog(
            requireContext(),
            error,
            viewModel::onAuthErrorDialogRetryClick
        )
    }
}