package com.example.swiftyproteins.presentation.navigation

import android.graphics.Color
import com.example.swiftyproteins.domain.models.Atom
import com.google.ar.sceneform.math.Vector3
import com.google.ar.sceneform.rendering.Vertex

sealed class FromProtein : Action {
    sealed class Navigate: FromProtein() {
        object Back: Navigate()
    }

    sealed class Command: FromProtein() {
//        class ShowSphere(val name: String, val coordinate: Vector3, val color: Color)
//
//        class ShowCylinder()
    }
}