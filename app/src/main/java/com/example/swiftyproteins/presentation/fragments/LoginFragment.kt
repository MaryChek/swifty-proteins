package com.example.swiftyproteins.presentation.fragments

import com.example.swiftyproteins.presentation.fragments.base.BaseFragment
import com.example.swiftyproteins.presentation.navigation.FromLogin
import com.example.swiftyproteins.presentation.navigation.Screens

class LoginFragment: BaseFragment<FromLogin>() {

    override fun handleAction(action: FromLogin) {
        when (action) {
            is FromLogin.Navigate.ProteinList -> {
//                router.navigateTo(Screens.ProteinList)
            }
        }
    }
}