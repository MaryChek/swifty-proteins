package com.example.swiftyproteins.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import com.example.swiftyproteins.R
import com.example.swiftyproteins.databinding.FragmentProteinViewBinding
import com.example.swiftyproteins.presentation.activity.MainActivity
import com.example.swiftyproteins.presentation.dialog.DialogCreator
import com.example.swiftyproteins.presentation.getColor
import com.example.swiftyproteins.presentation.fragments.base.BaseScreenStateFragment
import com.example.swiftyproteins.presentation.models.Protein
import com.example.swiftyproteins.presentation.models.ProteinError
import com.example.swiftyproteins.presentation.models.State
import com.example.swiftyproteins.presentation.navigation.FromProtein
import com.example.swiftyproteins.presentation.scene.SceneRender
import com.example.swiftyproteins.presentation.viewmodels.ProteinViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior

import androidx.constraintlayout.widget.ConstraintLayout
import com.example.swiftyproteins.data.model.AtomsInfo


class ProteinFragment : BaseScreenStateFragment<FromProtein, Protein, ProteinViewModel>() {

    private var binding: FragmentProteinViewBinding? = null
    private var sceneRender: SceneRender? = null
    private var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>? = null

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
        binding?.bottomSheet?.bottomSheet?.let { bottomSheet ->
            bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)
        }
    }

    override fun setupObserve() {
        super.setupObserve()
        viewModel?.atomInfo?.observe(viewLifecycleOwner, ::handleAtomInfo)
    }

    private fun handleAtomInfo(model: AtomsInfo.AtomInfo) {
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
            is FromProtein.Command.ShowNotFoundErrorDialog ->
                showNotFoundDialog(action.error)
            is FromProtein.Command.ShowNetworkErrorDialog ->
                showNetworkErrorDialog(action.error)
            is FromProtein.Command.ShowBottomSheet ->
                showBottomSheet()
            is FromProtein.Command.HideBottomSheet ->
                hideBottomSheet()
        }
    }

    private fun showBottomSheet() {
        binding?.bottomSheet?.bottomSheet?.isVisible = true
        binding?.root?.postDelayed({
            bottomSheetBehavior?.state = BottomSheetBehavior.STATE_COLLAPSED
        }, 500)
    }

    private fun hideBottomSheet() {
        bottomSheetBehavior?.state = BottomSheetBehavior.STATE_HIDDEN
    }

    private fun showNotFoundDialog(error: ProteinError) {
        viewModel?.let { vm ->
            DialogCreator().showNotFoundErrorDialog(
                requireContext(),
                error,
                vm::onNotFoundDialogCancelable
            )
        }
    }

    private fun showNetworkErrorDialog(error: ProteinError) {
        viewModel?.let { vm ->
            DialogCreator().showNetworkErrorDialog(
                requireContext(),
                error,
                vm::onNetworkErrorDialogCancelable,
                vm::onNetworkErrorDialogRetryClick
            )
        }
    }

    override fun handleState(state: State) {
        when (state) {
            is State.Loading -> binding?.progressBar?.isVisible = true
            is State.Success -> binding?.progressBar?.isVisible = false
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