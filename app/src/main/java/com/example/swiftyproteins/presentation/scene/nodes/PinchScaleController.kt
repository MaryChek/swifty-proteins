package com.example.swiftyproteins.presentation.scene.nodes

import com.google.ar.sceneform.ux.PinchGestureRecognizer
import com.google.ar.sceneform.ux.ScaleController

class PinchScaleController(
    private val transformableNode: DragTransformableNode,
    pinchRecognizer: PinchGestureRecognizer
) : ScaleController(transformableNode, pinchRecognizer)