//package com.example.swiftyproteins.presentation.activity
//
//import android.app.Activity
//import android.app.ActivityManager
//import android.content.Context
//import android.net.Uri
//import android.os.Build
//import android.os.Bundle
//import android.util.ArraySet
//import android.util.Log
//import android.view.Gravity
//import android.view.MotionEvent
//import android.widget.Toast
//import com.google.android.filament.gltfio.Animator
//import com.google.android.filament.gltfio.FilamentAsset
//import com.google.ar.core.HitResult
//import com.google.ar.core.Plane
//import com.google.ar.sceneform.AnchorNode
//import com.google.ar.sceneform.FrameTime
//import com.google.ar.sceneform.Node
//import com.google.ar.sceneform.math.Vector3
//import com.google.ar.sceneform.rendering.Color
//import com.google.ar.sceneform.rendering.ModelRenderable
//import com.google.ar.sceneform.rendering.Renderable
//import com.google.ar.sceneform.rendering.ViewRenderable
//import com.google.ar.sceneform.ux.ArFragment
//import com.google.ar.sceneform.ux.TransformableNode
//import java.lang.AssertionError
//import java.lang.ref.WeakReference
//import java.util.*
//import java.util.function.Consumer
//import java.util.function.Function
//import androidx.appcompat.app.AppCompatActivity
//import com.example.swiftyproteins.R
//import java.util.concurrent.TimeUnit
//
//
///**
// * This is an example activity that uses the Sceneform UX package to make common AR tasks easier.
// */
//class SceneActivity : AppCompatActivity() {
//    private var arFragment: ArFragment? = null
//    private var renderable: Renderable? = null
//
//    private class AnimationInstance(
//        var animator: Animator,
//        index: Int,
//        var startTime: Long
//    ) {
//        var duration: Float
//        var index: Int
//
//        init {
//            duration = animator.getAnimationDuration(index)
//            this.index = index
//        }
//    }
//
//    private val animators: MutableSet<AnimationInstance> = ArraySet()
//    private val colors = Arrays.asList(
//        Color(0f, 0f, 0f, 1f),
//        Color(1f, 0f, 0f, 1f),
//        Color(0f, 1f, 0f, 1f),
//        Color(0f, 0f, 1f, 1f),
//        Color(1f, 1f, 0f, 1f),
//        Color(0f, 1f, 1f, 1f),
//        Color(1f, 0f, 1f, 1f),
//        Color(1f, 1f, 1f, 1f)
//    )
//    private var nextColor = 0
//
//    // CompletableFuture requires api level 24
//    // FutureReturnValueIgnored is not valid
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        if (!checkIsSupportedDeviceOrFinish(this)) {
//            return
//        }
//        setContentView(R.layout.activity_ux)
//        arFragment = supportFragmentManager.findFragmentById(R.id.ux_fragment) as ArFragment
//        val weakActivity = WeakReference(this)
//        ModelRenderable.builder()
//            .setSource(
//                this,
//                Uri.parse(
//                    "https://storage.googleapis.com/ar-answers-in-search-models/static/Tiger/model.glb"
//                )
//            )
//            .setIsFilamentGltf(true)
//            .build()
//            .thenAccept { modelRenderable ->
//                val activity = weakActivity.get()
//                if (activity != null) {
//                    activity.renderable = modelRenderable
//                }
//            }
//            .exceptionally { throwable ->
//                val toast: Toast = Toast.makeText(
//                    this,
//                    "Unable to load Tiger renderable",
//                    Toast.LENGTH_LONG
//                )
//                toast.setGravity(Gravity.CENTER, 0, 0)
//                toast.show()
//                null
//            }
//        arFragment!!.setOnTapArPlaneListener { hitResult: HitResult, plane: Plane?, motionEvent: MotionEvent? ->
//            if (renderable == null) {
//                return@setOnTapArPlaneListener
//            }
//
//            // Create the Anchor.
//            val anchor = hitResult.createAnchor()
//            val anchorNode =
//                AnchorNode(anchor)
//            anchorNode.setParent(arFragment!!.arSceneView.scene)
//
//            // Create the transformable model and add it to the anchor.
//            val model =
//                TransformableNode(
//                    arFragment!!.transformationSystem
//                )
//            model.setParent(anchorNode)
//            model.renderable = renderable
//            model.select()
////            val filamentAsset: FilamentAsset = model.renderable?
//            val filamentAsset: FilamentAsset? =
//                model.renderableInstance?.filamentAsset
//            filamentAsset?.let {
//                if (filamentAsset.animator.animationCount > 0) {
//                    animators.add(
//                        AnimationInstance(
//                            filamentAsset.animator,
//                            0,
//                            System.nanoTime()
//                        )
//                    )
//                }
//            }
//            val color = colors[nextColor]
//            nextColor++
//            for (i in 0 until renderable!!.submeshCount) {
//                val material =
//                    renderable!!.getMaterial(i)
//                material.setFloat4("baseColorFactor", color)
//            }
//            val tigerTitleNode = Node()
//            tigerTitleNode.setParent(model)
//            tigerTitleNode.isEnabled = false
//            tigerTitleNode.localPosition = Vector3(
//                0.0f,
//                1.0f,
//                0.0f
//            )
//            ViewRenderable.builder()
//                .setView(this, R.layout.card_view)
//                .build()
//                .thenAccept(
//                    Consumer { renderable: ViewRenderable? ->
//                        tigerTitleNode.renderable = renderable
//                        tigerTitleNode.isEnabled = true
//                    })
//                .exceptionally(
//                    Function<Throwable, Void> { throwable: Throwable? ->
//                        throw AssertionError(
//                            "Could not load card view.",
//                            throwable
//                        )
//                    }
//                )
//        }
//        arFragment?.apply {
//            arSceneView
//                .scene
//                .addOnUpdateListener { frameTime: FrameTime? ->
//                    val time = System.nanoTime()
//                    for (animator: AnimationInstance in animators) {
//                        animator.animator.applyAnimation(
//                            animator.index,
//                            ((time - animator.startTime) / TimeUnit.SECONDS.toNanos(1)).toFloat()
//                                    % animator.duration
//                        )
//                        animator.animator.updateBoneMatrices()
//                    }
//                }
//        }
//    }
//
//    companion object {
//        private val TAG = "GltfActivity"
//        private const val MIN_OPENGL_VERSION = 3.0
//
//        /**
//         * Returns false and displays an error message if Sceneform can not run, true if Sceneform can run
//         * on this device.
//         *
//         *
//         * Sceneform requires Android N on the device as well as OpenGL 3.0 capabilities.
//         *
//         *
//         * Finishes the activity if Sceneform can not run
//         */
//        fun checkIsSupportedDeviceOrFinish(activity: Activity): Boolean {
//            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
//                Log.e(TAG, "Sceneform requires Android N or later")
//                Toast.makeText(activity, "Sceneform requires Android N or later", Toast.LENGTH_LONG)
//                    .show()
//                activity.finish()
//                return false
//            }
//            val openGlVersionString =
//                (activity.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager)
//                    .deviceConfigurationInfo
//                    .glEsVersion
//            if (openGlVersionString.toDouble() < MIN_OPENGL_VERSION) {
//                Log.e(TAG, "Sceneform requires OpenGL ES 3.0 later")
//                Toast.makeText(
//                    activity,
//                    "Sceneform requires OpenGL ES 3.0 or later",
//                    Toast.LENGTH_LONG
//                )
//                    .show()
//                activity.finish()
//                return false
//            }
//            return true
//        }
//    }
//}