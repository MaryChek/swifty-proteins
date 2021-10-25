package com.example.swiftyproteins.presentation.bottomsheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.swiftyproteins.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class AtomInfoBottomSheet : BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.bottom_sheet_atom_info, container, false)

    companion object {
        const val TAG = "ModalBottomSheet"
    }
}