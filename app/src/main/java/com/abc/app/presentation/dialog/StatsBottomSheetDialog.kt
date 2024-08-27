package com.abc.app.presentation.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.abc.app.R
import com.abc.app.data.model.Stats
import com.abc.app.databinding.BottomSheetLayoutBinding
import com.abc.app.presentation.adapters.BottomSheetAdapter
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class StatsBottomSheetDialog(private val stats: Stats) :
    BottomSheetDialogFragment() {
    private lateinit var binding: BottomSheetLayoutBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = BottomSheetLayoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner.lifecycleScope.launch {
             binding.txtTitle.text = getString(
                 R.string.no_of_funds_organization_items_top_3_characters,
                 "${stats.pageCount}"
             )
            val adapter = BottomSheetAdapter(stats)
            binding.bottomSheetRecyclerView.adapter = adapter
        }
    }
}