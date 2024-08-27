package com.abc.app.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.abc.app.databinding.FragmentListBinding
import com.abc.app.domain.common.Result
import com.abc.app.presentation.adapters.CarouselAdapter
import com.abc.app.presentation.adapters.DataListAdapter
import com.abc.app.presentation.dialog.StatsBottomSheetDialog
import com.abc.app.presentation.utils.SnackbarUtils
import com.abc.app.presentation.viewmodel.FundsViewModel
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FundsListFragment : Fragment() {
    private val binding by lazy { FragmentListBinding.inflate(layoutInflater) }
    private val viewModel: FundsViewModel by viewModels()
    private val recordListAdapter by lazy {
        DataListAdapter { isEmpty ->
            binding.tvNoResultFound.isVisible = isEmpty
        }
    }
    private val carouselAdapter by lazy { CarouselAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListeners()
        setupRecyclerView()
        setupCarousel()
        subscribeUi()
    }

    private fun setupListeners() = with(binding) {
        etFilter.addTextChangedListener { editable ->
            viewModel.filterListData(editable.toString())
        }

        fab.setOnClickListener {
            val stats = viewModel.getStatsForCurrentData()
            if (stats != null) {
                StatsBottomSheetDialog(stats).show(
                    parentFragmentManager,
                    "ModalBottomSheet"
                )
            }
        }
    }

    private fun setupRecyclerView() = with(binding.rvRecords) {
        layoutManager = LinearLayoutManager(context)
        adapter = recordListAdapter
    }

    private fun subscribeUi() = viewLifecycleOwner.lifecycleScope.launch {
        launch {
            viewModel.filteredFundsState.collect { result ->
                when (result) {
                    is Result.Success -> {
                        binding.progressBar.visibility = View.GONE
                        recordListAdapter.submitList(result.data)
                    }
                        is Result.Error -> {
                        binding.progressBar.visibility = View.GONE
                        SnackbarUtils.showSnackbar(binding.root, result.exception)
                        recordListAdapter.submitList(emptyList())

                    }
                    Result.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }
                }
            }
        }

        launch {
            viewModel.fundsState.collect { result ->
                if (result is Result.Success) {
                    carouselAdapter.submitList(result.data)
                }
            }
        }
    }

    private fun setupCarousel() = with(binding) {
        vpImageCarousel.adapter = carouselAdapter
        TabLayoutMediator(tlIndicator, vpImageCarousel) { _, _ -> }.attach()
        vpImageCarousel.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                etFilter.setText("")
                viewModel.getData(position)
            }
        })
    }
}