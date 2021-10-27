package com.example.swiftyproteins.presentation.dialog

import android.content.Context
import com.example.swiftyproteins.R
import com.example.swiftyproteins.presentation.models.ProteinError

class DialogCreator {
    fun showNetworkErrorDialog(
        context: Context,
        error: ProteinError,
        onCancelClick: () -> Unit,
        onRetryClick: () -> Unit
    ): CommonDialog =
        CommonDialog()
            .setTitle(context.getString(error.getTitleResId()))
            .setMessage(error.getErrorMessageResId())
            .setNegativeButtonTitle(R.string.cancel)
            .setPositiveButtonTitle(R.string.retry)
            .setFunctionOnPositive(onRetryClick)
            .setFunctionOnNegative(onCancelClick)
            .showDialog(context)

    fun showNotFoundErrorDialog(
        context: Context,
        error: ProteinError,
        onButtonClick: () -> Unit
    ): CommonDialog =
        CommonDialog()
            .setTitle(context.getString(error.getTitleResId()))
            .setMessage(error.getErrorMessageResId())
            .setPositiveButtonTitle(R.string.ok)
            .setFunctionOnPositive(onButtonClick)
            .showDialog(context)
}