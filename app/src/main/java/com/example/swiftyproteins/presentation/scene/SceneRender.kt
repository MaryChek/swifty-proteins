package com.example.swiftyproteins.presentation.scene

import android.content.Context
import android.graphics.drawable.ColorDrawable
import com.google.ar.sceneform.Node
import com.google.ar.sceneform.SceneView
import com.google.ar.sceneform.math.Quaternion
import com.google.ar.sceneform.math.Vector3
import com.google.ar.sceneform.rendering.*

class SceneRender {
    private var sceneView: SceneView? = null
    private var onNodeTouchListener: ((Node) -> Unit)? = null

    fun initSceneView(scene: SceneView): SceneRender {
        sceneView = scene
        sceneView?.scene?.camera?.worldPosition = DEFAULT_CAMERA_POSITION
        return this
    }

    fun setBackground(color: Int): SceneRender {
        sceneView?.background = ColorDrawable(color)
        return this
    }

    fun setOnNodeTouchListener(onTouch: (Node) -> Unit): SceneRender {
        onNodeTouchListener = onTouch
        initListener()
        return this
    }

    private fun initListener() {
        sceneView?.scene?.addOnPeekTouchListener { hitTestResult, motionEvent ->
            hitTestResult.node?.let { node ->
                if (node.name.isNotBlank()) {
                    onNodeTouchListener?.invoke(node)
                }
            }
        }
    }

    fun setSphere(context: Context, name: String, position: Vector3, color: Int) {
        MaterialFactory.makeOpaqueWithColor(context, Color(color))
            .thenAccept { material ->
                addModelSphereToScene(makeSphere(material), position, name)
            }
    }

    private fun makeSphere(material: Material): ModelRenderable =
        ShapeFactory.makeSphere(SPHERE_RADIUS, Vector3.zero(), material)

    private fun addModelSphereToScene(model: ModelRenderable, position: Vector3, name: String) {
        val node = Node().apply {
            setParent(scene)
            worldPosition = position
            localScale = SPHERE_SCALE
            this.name = name
            renderable = model
        }
        sceneView?.scene?.addChild(node)
    }

    fun setCylinder(context: Context, pointStart: Vector3, pointEnd: Vector3, color: Int) {
        MaterialFactory.makeOpaqueWithColor(context, Color(color))
            .thenAccept { material ->
                val differencePoints: Vector3 = Vector3.subtract(pointStart, pointEnd)
                val position: Vector3 = Vector3.add(pointStart, pointEnd).scaled(HALF)
                val model: ModelRenderable = makeCylinder(differencePoints.length(), material)
                addCylinderToScene(model, position, getCylinderRotation(differencePoints))
            }
    }

    private fun makeCylinder(height: Float, material: Material): ModelRenderable =
        ShapeFactory.makeCylinder(CYLINDER_RADIUS, height, Vector3.zero(), material)

    private fun getCylinderRotation(differencePoints: Vector3): Quaternion {
        val direction = differencePoints.normalized()
        val lookRotation = Quaternion.lookRotation(direction, Vector3.up())
        val quaterRotation = Quaternion.axisAngle(AXIS_X, AXIS_ROTATION_ANGLE)
        return Quaternion.multiply(lookRotation, quaterRotation)
    }

    private fun addCylinderToScene(model: ModelRenderable, pos: Vector3, rotation: Quaternion) {
        val node = Node().apply {
            setParent(scene)
            worldPosition = pos
            renderable = model
            worldRotation = rotation
        }
        sceneView?.scene?.addChild(node)
    }

    fun onPause() {
        sceneView?.pause()
    }

    fun onResume() {
        sceneView?.resume()
    }

    fun onDestroy() {
        sceneView?.destroy()
    }

    companion object {
        private val AXIS_X = Vector3(1.0f, 0.0f, 0.0f)
        private const val AXIS_ROTATION_ANGLE = 90f
        private const val HALF = .5f
        private const val SPHERE_RADIUS = .1F
        private val SPHERE_SCALE = Vector3(3f, 3f, 3f)
        private const val CYLINDER_RADIUS = .1F
        private val DEFAULT_CAMERA_POSITION = Vector3(0f, 0f, 20f)
    }
}