package com.example.swiftyproteins.presentation.activity

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.swiftyproteins.databinding.ActivityUxBinding
import com.google.ar.sceneform.Node
import com.google.ar.sceneform.Scene
import com.google.ar.sceneform.math.Quaternion
import com.google.ar.sceneform.math.Vector3
import com.google.ar.sceneform.rendering.*
import com.google.ar.sceneform.ux.ArFragment

class ActivityUx : AppCompatActivity() {

    private lateinit var binding: ActivityUxBinding
    private lateinit var fragmentUx: ArFragment
    lateinit var scene: Scene

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUxBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
//        fragmentUx = supportFragmentManager.findFragmentById(R.id.ux_fragment) as ArFragment
    }

    private fun init() {
        scene = binding.sceneView.scene
        val sphereOnePos = Vector3(-1f, -1f, 0f)
        val sphereTwoPos = Vector3(1f, 1f, -2f)
        scene.camera.worldPosition = Vector3(0f, 0f, 4f)
        makeSphere(sphereOnePos, Color.WHITE)
        makeSphere(sphereTwoPos, Color.BLACK)
        makeSphere(Vector3(0f, 0f, 0f), Color.BLACK)
        cylinderBetweenPoints(sphereOnePos, sphereTwoPos)

    }

    private fun initTouchListeners() {
//        fragmentUx.setOnTapArPlaneListener { hitResult, plane, motionEvent ->
//            val point: Anchor = hitResult.createAnchor()
//
//            createModel()
//        }
    }

    private fun cylinderBetweenPoints(point1: Vector3?, point2: Vector3?) {
        val lineNode = Node()

        val difference = Vector3.subtract(point1, point2)
        val directionFromTopToBottom = difference.normalized()
        val rotationFromAToB = Quaternion.lookRotation(directionFromTopToBottom, Vector3.up())

        MaterialFactory.makeOpaqueWithColor(this, Color(Color.parseColor("#ffa71c")))
            .thenAccept { material ->
                val lineRenderable = ShapeFactory.makeCylinder(
                    0.05f, difference.length(),
                    Vector3.zero(), material
                )
                lineNode.setParent(scene)
                lineNode.renderable = lineRenderable
                lineNode.localPosition = Vector3.add(point1, point2).scaled(.5f)
                lineNode.worldRotation = Quaternion.multiply(
                        rotationFromAToB,
                        Quaternion.axisAngle(Vector3(1.0f, 0.0f, 0.0f), 90f)
                    )
            }

    }
    private fun makeSphere(pos: Vector3, res: Int) {
        MaterialFactory.makeOpaqueWithColor(
            this,
            com.google.ar.sceneform.rendering.Color(res)
        )
            .thenAccept { material ->
                addModelToScene(
                    ShapeFactory.makeSphere(
                        0.1f,
                        Vector3.zero(),
                        material
                    ), pos
                )

            }
    }

    private fun addCylinderModelToScene(
        model: ModelRenderable,
        center: Vector3,
        posStart: Vector3,
        posEnd: Vector3
    ) {
        val node = Node().apply {
            setParent(scene)
            worldPosition = center
//            localScale = Vector3(3f, 3f, 3f)
            name = "Cupcake"
            renderable = model
            localRotation = Quaternion.lookRotation(posStart, posEnd)
        }

        scene.addChild(node)

    }

    private fun addModelToScene(model: ModelRenderable, pos: Vector3) {
        val node = Node().apply {
            setParent(scene)
            worldPosition = pos
            localScale = Vector3(3f, 3f, 3f)
            name = "Cupcake"
            renderable = model
        }

        scene.addChild(node)
    }

    override fun onPause() {
        super.onPause()
        binding.sceneView.pause()
    }

    override fun onResume() {
        super.onResume()
        binding.sceneView.resume()
    }
}