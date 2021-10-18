package com.example.swiftyproteins.presentation.fragments

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import com.example.swiftyproteins.R
import com.example.swiftyproteins.databinding.FragmentProteinViewBinding
import com.example.swiftyproteins.domain.models.Atom
import com.example.swiftyproteins.presentation.App
import com.example.swiftyproteins.presentation.getColor
import com.example.swiftyproteins.presentation.logD
import com.example.swiftyproteins.presentation.fragments.base.BaseFragment
import com.example.swiftyproteins.presentation.navigation.FromProtein
import com.example.swiftyproteins.presentation.scene.SceneRender
import com.example.swiftyproteins.presentation.toVec3
import com.google.ar.sceneform.Node
import com.google.ar.sceneform.math.Vector3

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
    }

    //TODO move to ViewModel
    private fun getProtein() {
        //TODO get proteinName from file ligands.txt
        val proteinName = "002"
        getFile(proteinName)
    }

    private fun getFile(proteinName: String) {
        val app: App = requireActivity().applicationContext as App
        val interactor = app.interactor
        interactor.getProteinByName(proteinName) { protein ->
            showProtein(protein)
        }
    }

    private fun showProtein(protein: List<Atom>) {
        protein.forEachIndexed { index, atom ->
            if (atom.base != Atom.BaseAtom.H) {
                val coordinate: Vector3 = atom.coordinate.toVec3()
                sceneRender?.setSphere(
                    requireContext(),
                    atom.name,
                    coordinate,
                    getAtomColor(atom.base)
                )
                atom.connectList.forEach { atomId ->
                    if (atomId.toInt() > index.minus(1)) {
                        protein.find { endAtom ->
                            endAtom.id == atomId && endAtom.base != Atom.BaseAtom.H
                        }?.coordinate?.toVec3()?.let { coordinateEnd ->
                            sceneRender?.setCylinder(
                                requireContext(),
                                coordinate,
                                coordinateEnd,
                                getColor(R.color.atom_connection)
                            )
                        }
                    }
                }
            }
        }
    }

    //TODO move to ViewModel
    private fun getAtomColor(atom: Atom.BaseAtom) =
        when (atom) {
            Atom.BaseAtom.C -> getColor(R.color.atom_connection)
            Atom.BaseAtom.O -> Color.RED
            Atom.BaseAtom.H -> Color.WHITE
            Atom.BaseAtom.N -> Color.BLUE
            Atom.BaseAtom.P -> Color.YELLOW
            Atom.BaseAtom.F -> Color.GREEN
            Atom.BaseAtom.OTHER -> Color.MAGENTA
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