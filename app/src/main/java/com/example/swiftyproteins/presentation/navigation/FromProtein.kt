package com.example.swiftyproteins.presentation.navigation

import android.net.Uri
import com.example.swiftyproteins.presentation.models.ProteinError

sealed class FromProtein : Action {
    sealed class Navigate : FromProtein() {
        object Back : Navigate()
    }

    sealed class Command : FromProtein() {
        class ShowNetworkErrorDialog(val error: ProteinError.NetworkError) : Command()

        class ShowNotFoundErrorDialog(val error: ProteinError.ProteinNotFound) : Command()

        object ShowBottomSheet: Command()

        object HideBottomSheet: Command()

        object MakeSceneScreenBitmap: Command()

        object MakeUiScreenBitmap: Command()

        class ShareScreenByUri(val uri: Uri): Command()
    }
}