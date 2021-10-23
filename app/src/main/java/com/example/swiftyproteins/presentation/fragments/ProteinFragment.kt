package com.example.swiftyproteins.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import com.example.swiftyproteins.R
import com.example.swiftyproteins.databinding.FragmentProteinViewBinding
import com.example.swiftyproteins.domain.models.Atom
import com.example.swiftyproteins.presentation.getColor
import com.example.swiftyproteins.presentation.fragments.base.BaseScreenStateFragment
import com.example.swiftyproteins.presentation.models.Protein
import com.example.swiftyproteins.presentation.navigation.FromProtein
import com.example.swiftyproteins.presentation.scene.SceneRender
import com.example.swiftyproteins.presentation.viewmodels.ProteinViewModel

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
        val proteinName: String? = arguments?.getString(ARG_PROTEIN_NAME)
        proteinName?.let {
            viewModel?.onViewCreated(proteinName)
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
    }

    override fun handleModel(model: Protein) {
        model.atoms.forEach { atom ->
            sceneRender?.setSphere(
                requireContext(),
                atom.name,
                atom.coordinate,
                atom.color
            )
        }
        model.atomConnections.forEach { atomConnection ->
            sceneRender?.setCylinder(
                requireContext(),
                atomConnection.coordinateTop,
                atomConnection.coordinateBottom,
                atomConnection.color
            )
        }
    }

    private fun showProtein(protein: List<Atom>) {
//        protein.forEachIndexed { index, atom ->
//            if (atom.base != Atom.BaseAtom.H) {
//                val coordinate: Vector3 = atom.coordinate.toVec3()
//                sceneRender?.setSphere(
//                    requireContext(),
//                    atom.name,
//                    coordinate,
//                    getAtomColor(atom.base)
//                )
//                atom.connectList.forEach { atomId ->
//                    if (atomId.toInt() > index.minus(1)) {
//                        protein.find { endAtom ->
//                            endAtom.id == atomId && endAtom.base != Atom.BaseAtom.H
//                        }?.coordinate?.toVec3()?.let { coordinateEnd ->
//                            sceneRender?.setCylinder(
//                                requireContext(),
//                                coordinate,
//                                coordinateEnd,
//                                getColor(R.color.atom_connection)
//                            )
//                        }
//                    }
//                }
//            }
//        }
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