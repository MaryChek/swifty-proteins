package com.example.swiftyproteins.presentation.dialog

import android.content.Context
import androidx.annotation.StringRes
import com.example.swiftyproteins.R
import com.example.swiftyproteins.presentation.models.ProteinError

class DialogCreator {

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

    fun showSetLockPassDialog(
        context: Context,
        onPositiveClick: () -> Unit
    ) : CommonDialog =
        showCancelableAndContinueDialog(
            context = context,
            title = context.getString(R.string.warning),
            messageResId = R.string.message_setup_pass_lock,
            onCancelClick = null,
            onContinueClick = onPositiveClick
        )

    fun showAuthErrorDialog(
        context: Context,
        error: ProteinError.AuthError,
        onRetryClick: () -> Unit
    ): CommonDialog =
        showErrorCancelableAndContinueDialog(context, error,  null, onRetryClick)

    fun showNetworkErrorDialog(
        context: Context,
        error: ProteinError,
        onCancelClick: () -> Unit,
        onRetryClick: () -> Unit
    ): CommonDialog =
        showErrorCancelableAndContinueDialog(context, error, onCancelClick, onRetryClick)

    private fun showErrorCancelableAndContinueDialog(
        context: Context,
        error: ProteinError,
        onCancelClick: (() -> Unit)?,
        onRetryClick: () -> Unit
    ) : CommonDialog =
        showCancelableAndContinueDialog(
            context = context,
            title = context.getString(error.getTitleResId()),
            messageResId = error.getErrorMessageResId(),
            positiveButtonTitleResId = R.string.retry,
            onCancelClick = onCancelClick,
            onContinueClick = onRetryClick
        )

    private fun showCancelableAndContinueDialog(
        context: Context,
        title: String,
        @StringRes messageResId: Int,
        @StringRes positiveButtonTitleResId: Int = R.string.__arcore_continue,
        onCancelClick: (() -> Unit)?,
        onContinueClick: () -> Unit
    ): CommonDialog =
        CommonDialog()
            .setTitle(title)
            .setMessage(messageResId)
            .setNegativeButtonTitle(R.string.cancel)
            .setPositiveButtonTitle(positiveButtonTitleResId)
            .setFunctionOnPositive(onContinueClick)
            .setFunctionOnNegative(onCancelClick)
            .showDialog(context)
}