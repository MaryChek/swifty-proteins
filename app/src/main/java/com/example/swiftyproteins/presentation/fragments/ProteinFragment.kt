package com.example.swiftyproteins.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import com.example.swiftyproteins.R
import com.example.swiftyproteins.databinding.FragmentProteinViewBinding
import com.example.swiftyproteins.presentation.activity.MainActivity
import com.example.swiftyproteins.presentation.getColor
import com.example.swiftyproteins.presentation.fragments.base.BaseScreenStateFragment
import com.example.swiftyproteins.presentation.models.Protein
import com.example.swiftyproteins.presentation.navigation.FromProtein
import com.example.swiftyproteins.presentation.scene.SceneRender
import com.example.swiftyproteins.presentation.viewmodels.ProteinViewModel
import com.google.ar.sceneform.math.Vector3

class ProteinFragment : BaseScreenStateFragment<FromProtein, Protein, ProteinViewModel>() {

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
        initProtein()
        binding?.toolbar?.setNavigationOnClickListener {
            viewModel?.onBackClick()
        }
    }

    private fun initScene() {
        binding?.sceneView?.let { sceneView ->
            sceneRender = SceneRender()
                .initSceneView(sceneView)
                .setBackground(getColor(R.color.color_background_scene))
                .setOnNodeTouchListener(requireContext()) { node ->
                    viewModel?.onNodeTouch(node)
                }
                .setDisplayMetrics(resources.displayMetrics)
        }
//        binding?.sceneView?.scene?.camera?.farClipPlane = 100f
//        binding?.sceneView?.scene?.camera?.worldPosition = Vector3(0f, 0f, 200f)
    }

    private fun initProtein() {
        //Todo remove and get argument on init
        val proteinName: String? = arguments?.getString(ARG_PROTEIN_NAME)
        proteinName?.let {
            viewModel?.onViewCreated(proteinName)
            setupToolbarTitle(proteinName)
        }
    }

    private fun setupToolbarTitle(proteinName: String) {
        binding?.toolbar?.title = proteinName
    }

    override fun handleModel(model: Protein) {
        sceneRender?.setCameraPosition(model.cameraPosition)
        model.atoms.forEach { atom ->
            sceneRender?.setSphere(
                requireContext(),
                atom.name,
                atom.coordinate,
                getColor(atom.colorResId)
            )
        }
        model.atomConnections.forEach { atomConnection ->
            sceneRender?.setCylinder(
                requireContext(),
                atomConnection.coordinateTop,
                atomConnection.coordinateBottom,
                getColor(atomConnection.colorResId)
            )
        }
    }

    override fun handleAction(action: FromProtein) {
        when (action) {
            is FromProtein.Navigate.Back -> {
                val fragment = ProteinListFragment()
                (activity as MainActivity).supportFragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainerView, fragment)
                    .commit()
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

    override fun getViewModelClass(): Class<ProteinViewModel> =
        ProteinViewModel::class.java

    companion object {
        private const val ARG_PROTEIN_NAME = "protein_name"

        fun newInstance(proteinName: String) =
            ProteinFragment().apply {
                arguments = bundleOf(ARG_PROTEIN_NAME to proteinName)
            }
    }
}