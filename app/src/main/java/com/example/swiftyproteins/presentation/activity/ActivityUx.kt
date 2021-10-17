package com.example.swiftyproteins.presentation.activity

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.swiftyproteins.R
import com.example.swiftyproteins.databinding.ActivityUxBinding
import com.google.ar.sceneform.Node
import com.google.ar.sceneform.Scene
import com.google.ar.sceneform.math.Quaternion
import com.google.ar.sceneform.math.Vector3
import com.google.ar.sceneform.rendering.Material
import com.google.ar.sceneform.rendering.MaterialFactory
import com.google.ar.sceneform.rendering.ModelRenderable
import com.google.ar.sceneform.rendering.ShapeFactory
import com.google.ar.sceneform.rendering.Color as RenderColor

class ActivityUx : AppCompatActivity() {

//    private var binding: ActivityUxBinding? = null
//    private var scene: Scene? = null
//    private val logTag: String = this::class.java.simpleName
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        binding = ActivityUxBinding.inflate(layoutInflater)
//        setContentView(binding?.root)
//        initScene()
//        initTouchListeners()
//    }
//
//    private fun initScene() {
//        binding?.sceneView?.background = ColorDrawable(getColor(R.color.color_background_scene))
//        scene = binding?.sceneView?.scene
//        val sphereOnePos = Vector3(-1f, -1f, 0f)
//        val sphereTwoPos = Vector3(1f, 1f, -2f)
//        val sphereTreePos = Vector3(1f, -0.2f, 0f)
//        scene?.camera?.worldPosition = Vector3(0f, 0f, 4f)
//        makeSphere(sphereOnePos, Color.WHITE)
//        makeSphere(sphereTwoPos, Color.BLACK)
//        makeSphere(sphereTreePos, Color.RED)
//        cylinderBetweenPoints(sphereOnePos, sphereTreePos)
//        cylinderBetweenPoints(sphereTreePos, sphereTwoPos)
//    }
//
//    private fun initTouchListeners() =
//        scene?.addOnPeekTouchListener { hitResult, _ ->
//            hitResult?.node?.name?.let { nameNode ->
//                Log.d(logTag, "click on $nameNode")
//            }
//        }
//
//    private fun cylinderBetweenPoints(point1: Vector3, point2: Vector3) =
//        MaterialFactory.makeOpaqueWithColor(this, RenderColor(getColor(R.color.atom_connection)))
//            .thenAccept { material ->
//                val differencePoints: Vector3 = Vector3.subtract(point1, point2)
//                val position: Vector3 = Vector3.add(point1, point2).scaled(HALF)
//                val model: ModelRenderable = makeCylinder(differencePoints.length(), material)
//                addCylinderToScene(model, position, getCylinderRotation(differencePoints))
//            }
//
//    private fun makeCylinder(height: Float, material: Material): ModelRenderable =
//        ShapeFactory.makeCylinder(CYLINDER_RADIUS, height, Vector3.zero(), material)
//
//    private fun getCylinderRotation(differencePoints: Vector3): Quaternion {
//        val direction = differencePoints.normalized()
//        val lookRotation = Quaternion.lookRotation(direction, Vector3.up())
//        val quaterRotation = Quaternion.axisAngle(AXIS_X, AXIS_ROTATION_ANGLE)
//        return Quaternion.multiply(lookRotation, quaterRotation)
//    }
//
//    private fun addCylinderToScene(
//        model: ModelRenderable,
//        position: Vector3,
//        rotation: Quaternion
//    ) {
//        val node = Node().apply {
//            setParent(scene)
//            worldPosition = position
//            renderable = model
//            worldRotation = rotation
//        }
//        scene?.addChild(node)
//    }
//
//    private fun makeSphere(pos: Vector3, color: Int) =
//        MaterialFactory.makeOpaqueWithColor(this, RenderColor(color))
//            .thenAccept { material ->
//                addModelToScene(makeSphere(material), pos)
//            }
//
//    private fun makeSphere(material: Material): ModelRenderable =
//        ShapeFactory.makeSphere(SPHERE_RADIUS, Vector3.zero(), material)
//
//    private fun addModelToScene(model: ModelRenderable, position: Vector3/*name: String*/) {
//        val node = Node().apply {
//            setParent(scene)
//            worldPosition = position
//            localScale = SPHERE_SCALE
//            name = "Cupcake"
//            renderable = model
//        }
//        scene?.addChild(node)
//    }
//
//    override fun onPause() {
//        super.onPause()
//        binding?.sceneView?.pause()
//    }
//
//    override fun onResume() {
//        super.onResume()
//        binding?.sceneView?.resume()
//    }
//
//    override fun onDestroy() {
//        super.onDestroy()
//        binding?.sceneView?.destroy()
//        binding = null
//    }
//
//    companion object {
//        private val AXIS_X = Vector3(1.0f, 0.0f, 0.0f)
//        private const val AXIS_ROTATION_ANGLE = 90f
//        private const val HALF = .5f
//        private const val SPHERE_RADIUS = .1F
//        private val SPHERE_SCALE = Vector3(3f, 3f, 3f)
//        private const val CYLINDER_RADIUS = .1F
//    }
}