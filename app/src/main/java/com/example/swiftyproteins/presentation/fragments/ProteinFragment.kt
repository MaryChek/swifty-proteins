package com.example.swiftyproteins.presentation.fragments

import android.app.DownloadManager
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import com.example.swiftyproteins.R
import com.example.swiftyproteins.databinding.FragmentProteinViewBinding
import com.example.swiftyproteins.presentation.App
import com.example.swiftyproteins.presentation.getColor
import com.example.swiftyproteins.presentation.logD
import com.example.swiftyproteins.presentation.fragments.base.BaseFragment
import com.example.swiftyproteins.presentation.logE
import com.example.swiftyproteins.presentation.navigation.FromProtein
import com.example.swiftyproteins.presentation.scene.SceneRender
import com.google.ar.sceneform.Node
import com.google.ar.sceneform.math.Vector3
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.lang.Exception

class ProteinFragment : BaseFragment<FromProtein>() {

    private var binding: FragmentProteinViewBinding? = null
    private var sceneRender: SceneRender? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProteinViewBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initScene()
    }

    private fun initScene() {
        binding?.sceneView?.let { sceneView ->
            sceneRender = SceneRender()
                .initSceneView(sceneView)
                .setBackground(getColor(R.color.color_background_scene))
                .setOnNodeTouchListener(::onNodeTouch)
        }
        setNodesToScene()
    }

    //TODO move to viewModel
    private fun onNodeTouch(node: Node) {
        node.name?.let { nameNode ->
            logD("click on $nameNode")
        }
    }

    private fun setNodesToScene() {
        getProtein()

        val sphereOnePos = Vector3(1f, -2f, 0f)
        val sphereTwoPos = Vector3(1f, 1f, -2f)
        val sphereTreePos = Vector3(1f, -0.2f, 0f)
        sceneRender?.setSphere(requireContext(), "sph1", sphereOnePos, Color.WHITE)
        sceneRender?.setSphere(requireContext(), "sph2", sphereTwoPos, Color.BLACK)
        sceneRender?.setSphere(requireContext(), "sph3", sphereTreePos, Color.RED)
        val cylinderColor: Int = getColor(R.color.atom_connection)
        sceneRender?.setCylinder(requireContext(), sphereOnePos, sphereTreePos, cylinderColor)
        sceneRender?.setCylinder(requireContext(), sphereTreePos, sphereTwoPos, cylinderColor)
    }

    //TODO move to ViewModel
    private fun getProtein() {
        //TODO get proteinName from file ligands.txt
        val proteinName: String = "001"
        getFile(proteinName)
    }

    private fun getFile(proteinName: String) {
        val app: App = requireActivity().applicationContext as App
        val repository = app.repository
        repository.getAtomByName(proteinName) { body ->
            val inStream: InputStream = body.byteStream()
            val reader = BufferedReader(InputStreamReader(inStream))
            var result: String?
            var line: String? = reader.readLine()
            result = line
            while (reader.readLine().also { line = it } != null) {
                result += line
            }
            println(result)
            body.close()
        }
    }

    override fun handleAction(action: FromProtein) {
        when (action) {
            is FromProtein.Navigate.Back -> {
//                router.exit()
            }
        }
    }

    override fun onPause() {
        super.onPause()
        sceneRender?.onPause()
    }

    override fun onResume() {
        super.onResume()
        sceneRender?.onResume()
    }

    override fun onDestroy() {
        super.onDestroy()
        sceneRender?.onDestroy()
        binding = null
    }

    companion object {
        private const val ARG_PROTEIN_NAME = "protein_name"

        fun newInstance(proteinName: String) =
            ProteinFragment().apply {
                arguments = bundleOf(ARG_PROTEIN_NAME to proteinName)
            }
    }
}