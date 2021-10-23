package com.example.swiftyproteins.presentation.fragments

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import com.example.swiftyproteins.R
import com.example.swiftyproteins.databinding.FragmentProteinListBinding
import com.example.swiftyproteins.presentation.activity.MainActivity
import com.example.swiftyproteins.presentation.adapters.LigandListAdapter
import com.example.swiftyproteins.presentation.fragments.base.BaseScreenStateFragment
import com.example.swiftyproteins.presentation.hideKeyboard
import com.example.swiftyproteins.presentation.navigation.FromProteinList
import com.example.swiftyproteins.presentation.viewmodels.ProteinListViewModel

class ProteinListFragment :
    BaseScreenStateFragment<FromProteinList, List<String>, ProteinListViewModel>() {

    private var binding: FragmentProteinListBinding? = null
    private var adapter: LigandListAdapter? = null
    private lateinit var searchView: SearchView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProteinListBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbar()
        initList()
        viewModel?.onViewCreated()
    }

    private fun initToolbar() {
        binding?.detailToolbar?.let { toolbar ->
            (requireActivity() as MainActivity).apply {
                setSupportActionBar(toolbar)
                supportActionBar?.setHomeButtonEnabled(true)
                supportActionBar?.setDisplayHomeAsUpEnabled(true)
                supportActionBar?.title = ""
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.protein_list_menu, menu)
        val item: MenuItem = menu.findItem(R.id.app_bar_search)
        searchView = item.actionView as SearchView
        initOnChangeSearchTextListener()
        return super.onCreateOptionsMenu(menu, inflater)
    }

    private fun initOnChangeSearchTextListener() {
        searchView.setOnQueryTextListener(
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> viewModel?.onBackPressed()
            else -> return super.onOptionsItemSelected(item)
        }
        return true
    }

    private fun initList() {
        adapter = LigandListAdapter { name ->
            viewModel?.onProteinClick(name)
        }
        binding?.rvLigands?.adapter = adapter
    }

    override fun handleModel(model: List<String>) {
        adapter?.submitList(model)
        binding?.rvLigands?.smoothScrollToPosition(RV_SCROLL_POSITION)
    }

    override fun handleAction(action: FromProteinList) {
        when (action) {
            is FromProteinList.Navigate.Protein -> {
                //TODO remove and uncomment router
                binding?.root?.let {
                    val fragment = ProteinFragment.newInstance(action.proteinName)
                    (activity as MainActivity).supportFragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainerView, fragment)
                        .commit()
                }
//                router.navigateTo(action.proteinName)
            }
        }
    }

    override fun getViewModelClass() =
        ProteinListViewModel::class.java

    companion object {
        private const val RV_SCROLL_POSITION = 0
    }
}