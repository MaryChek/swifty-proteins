package com.example.swiftyproteins.presentation.models

import com.example.swiftyproteins.R

sealed class ProteinError {
    object NetworkError: ProteinError() {
        override fun getErrorMessageResId(): Int =
            R.string.network_error_message
    }

    object ProteinNotFound: ProteinError() {
        override fun getErrorMessageResId(): Int =
            R.string.missing_protein_error_message
    }

    open fun getErrorMessageResId():Int =
        R.string.unknown_error

    fun getTitleResId(): Int =
        R.string.error
}