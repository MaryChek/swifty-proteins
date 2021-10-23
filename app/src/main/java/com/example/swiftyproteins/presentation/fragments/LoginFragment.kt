package com.example.swiftyproteins.presentation.fragments

import com.example.swiftyproteins.presentation.fragments.base.BaseFragment
import com.example.swiftyproteins.presentation.navigation.FromLogin
import com.example.swiftyproteins.presentation.navigation.Screens
import com.example.swiftyproteins.presentation.viewmodels.LoginViewModel

class LoginFragment: BaseFragment<FromLogin, LoginViewModel>() {

    override fun handleAction(action: FromLogin) {
        when (action) {
            is FromLogin.Navigate.ProteinList -> {
//                router.navigateTo(Screens.ProteinList)
            }
        }
    }

    override fun getViewModelClass(): Class<LoginViewModel> =
        LoginViewModel::class.java
}