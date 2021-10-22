package com.example.swiftyproteins.presentation.scene

import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.util.DisplayMetrics
import com.example.swiftyproteins.presentation.scene.nodes.DragTransformableNode
import com.google.ar.sceneform.Node
import com.google.ar.sceneform.SceneView
import com.google.ar.sceneform.math.Quaternion
import com.google.ar.sceneform.math.Vector3
import com.google.ar.sceneform.rendering.*
import com.google.ar.sceneform.ux.FootprintSelectionVisualizer
import com.google.ar.sceneform.ux.TransformationSystem
import android.view.ScaleGestureDetector
import android.view.ScaleGestureDetector.OnScaleGestureListener

class SceneRender {
    private var sceneView: SceneView? = null
    private var onNodeTouchListener: ((Node) -> Unit)? = null
    private var transformationSystem: TransformationSystem? = null
    private var scaleGestureDetector: ScaleGestureDetector? = null
    private var scaleFactor = 1f

    //Слушатель получения scale при "щипке"
    private val onScaleGestureListener = object : OnScaleGestureListener {
        override fun onScale(detector: ScaleGestureDetector): Boolean {
            scaleFactor *= detector.scaleFactor
            scaleFactor =
                if (scaleFactor < 1) {
                    1F
                } else {
                    scaleFactor
                }

            scaleFactor = (scaleFactor * 100).toInt().toFloat() / 100

            val pos = sceneView?.scene?.camera?.forward?.scaled(scaleFactor)
            pos?.let {
                sceneView?.scene?.camera?.worldPosition = it
            }
            return true
        }

        override fun onScaleBegin(detector: ScaleGestureDetector): Boolean {
            return true
        }

        override fun onScaleEnd(detector: ScaleGestureDetector) {}
    }


    fun initSceneView(scene: SceneView): SceneRender {
        sceneView = scene
        sceneView?.scene?.camera?.worldPosition = DEFAULT_CAMERA_POSITION
        return this
    }

    fun setBackground(color: Int): SceneRender {
        sceneView?.background = ColorDrawable(color)
        return this
    }

    fun setDisplayMetrics(metrics: DisplayMetrics): SceneRender {
        transformationSystem = makeTransformationSystem(metrics)
        return this
    }

    private fun makeTransformationSystem(metrics: DisplayMetrics): TransformationSystem {
        val footprintSelectionVisualizer = FootprintSelectionVisualizer()
        return TransformationSystem(metrics, footprintSelectionVisualizer)
    }

    fun setOnNodeTouchListener(context: Context, onTouch: (Node) -> Unit): SceneRender {
        scaleGestureDetector = ScaleGestureDetector(context, onScaleGestureListener)
        onNodeTouchListener = onTouch
        initListeners()
        return this
    }

    private fun initListeners() {
        sceneView?.scene?.addOnPeekTouchListener { hitTestResult, motionEvent ->
            scaleGestureDetector?.onTouchEvent(motionEvent)
            transformationSystem?.onTouch(hitTestResult, motionEvent)
            hitTestResult.node?.let { node ->
                if (node.name.isNotBlank()) {
                    onNodeTouchListener?.invoke(node)
                }
            }
        }
    }

    /*
    * --------------------------------------------------------------------------
    * Method: spacing Parameters: MotionEvent Returns: float Description:
    * checks the spacing between the two fingers on touch
    * ----------------------------------------------------
    */
//    private fun spacing(event: MotionEvent): Float {
//        val x = event.getX(0) - event.getX(1)
//        val y = event.getY(0) - event.getY(1)
//        return kotlin.math.sqrt(x * x + y * y)
//    }

    /*
     * --------------------------------------------------------------------------
     * Method: midPoint Parameters: PointF object, MotionEvent Returns: void
     * Description: calculates the midpoint between the two fingers
     * ------------------------------------------------------------
     */
//    private fun midPoint(point: PointF, event: MotionEvent): PointF {
//        val x = event.getX(0) + event.getX(1)
//        val y = event.getY(0) + event.getY(1)
//        point[x / 2] = y / 2
//    }

    fun setSphere(context: Context, name: String, position: Vector3, color: Int) {
        MaterialFactory.makeOpaqueWithColor(context, Color(color))
            .thenAccept { material ->
                addModelSphereToScene(makeSphere(material), position, name)
            }
    }

    private fun makeSphere(material: Material): ModelRenderable =
        ShapeFactory.makeSphere(SPHERE_RADIUS, Vector3.zero(), material)

    private fun addModelSphereToScene(model: ModelRenderable, position: Vector3, name: String) {
        val node = getNode().apply {
            setParent(scene)
            worldPosition = position
            localScale = SPHERE_SCALE
            this.name = name
            renderable = model
        }
        addNodeToScene(node)
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
        val node = getNode().apply {
            setParent(scene)
            worldPosition = pos
            renderable = model
            worldRotation = rotation
        }
        addNodeToScene(node)
    }

    private fun addNodeToScene(node: Node) =
        sceneView?.scene?.apply {
            addChild(node)
            node.selectIfRequired()
        }

    private fun getNode(): Node =
        transformationSystem?.let { transform ->
            DragTransformableNode(DEFAULT_NODE_RADIUS, transform)
        } ?: Node()

    private fun Node.selectIfRequired() {
        if (this is DragTransformableNode) {
            select()
        }
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
        private const val DEFAULT_NODE_RADIUS = 15.0f
        private val AXIS_X = Vector3(1.0f, 0.0f, 0.0f)
        private const val AXIS_ROTATION_ANGLE = 90f
        private const val HALF = .5f
        private const val SPHERE_RADIUS = .15F
        private val SPHERE_SCALE = Vector3(4f, 4f, 4f)
        private const val CYLINDER_RADIUS = .1F
        private val DEFAULT_CAMERA_POSITION = Vector3(0f, 0f, 20f)
    }
}