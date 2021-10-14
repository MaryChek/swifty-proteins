package com.example.swiftyproteins.presentation.fragments

import com.example.swiftyproteins.presentation.fragments.base.BaseFragment
import com.example.swiftyproteins.presentation.navigation.FromProteinList

class ProteinListFragment: BaseFragment<FromProteinList>() {

    override fun handleAction(action: FromProteinList) {
        when (action) {
            is FromProteinList.Navigate.Protein -> {
//                router.navigateTo(action.proteinName)
            }
        }
    }
}