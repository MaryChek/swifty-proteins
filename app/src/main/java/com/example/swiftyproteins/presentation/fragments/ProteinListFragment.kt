package com.example.swiftyproteins.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import com.example.swiftyproteins.databinding.FragmentProteinListBinding
import com.example.swiftyproteins.presentation.adapters.LigandListAdapter
import com.example.swiftyproteins.presentation.fragments.base.BaseScreenStateFragment
import com.example.swiftyproteins.presentation.hideKeyboard
import com.example.swiftyproteins.presentation.navigation.FromProteinList
import com.example.swiftyproteins.presentation.viewmodels.ProteinListViewModel

class ProteinListFragment : BaseScreenStateFragment<FromProteinList, List<String>, ProteinListViewModel>() {

    private var binding: FragmentProteinListBinding? = null
    private var adapter: LigandListAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProteinListBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initList()
        initOnChangeSearchTextListener()
        viewModel?.onViewCreated()
    }

    private fun initList() {
        adapter = LigandListAdapter { name ->
            viewModel?.onProteinClick(name)
        }
        binding?.rvLigands?.adapter = adapter
    }

    private fun initOnChangeSearchTextListener() {
        binding?.svLigand?.setOnQueryTextListener(
            object : SearchView.OnQueryTextListener {
                override fun onQueryTextChange(enteredText: String?): Boolean {
                    if (enteredText != null) {
                        viewModel?.onSearchTextChanged(enteredText)
                    }
                    return false
                }

                override fun onQueryTextSubmit(query: String?): Boolean {
                    hideKeyboard()
                    return true
                }
            })
    }

    override fun handleModel(model: List<String>) {
        adapter?.submitList(model)
    }

    override fun handleAction(action: FromProteinList) {
        when (action) {
            is FromProteinList.Navigate.Protein -> {
//                router.navigateTo(action.proteinName)
            }
        }
    }

    override fun getViewModelClass() =
        ProteinListViewModel::class.java
}