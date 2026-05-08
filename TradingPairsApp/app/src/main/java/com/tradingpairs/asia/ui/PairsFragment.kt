package com.tradingpairs.asia.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.tradingpairs.asia.databinding.FragmentPairsBinding
import com.tradingpairs.asia.viewmodel.PairsViewModel

class PairsFragment : Fragment() {

    private var _binding: FragmentPairsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: PairsViewModel by activityViewModels()
    private lateinit var adapter: PairsAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentPairsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupSwipeRefresh()
        observeViewModel()
    }

    private fun setupRecyclerView() {
        adapter = PairsAdapter { pairWithQuotes ->
            PairDetailBottomSheet.newInstance(pairWithQuotes.pair.id)
                .show(childFragmentManager, "pair_detail")
        }
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter
    }

    private fun setupSwipeRefresh() {
        binding.swipeRefresh.setOnRefreshListener {
            viewModel.fetchPrices()
        }
        binding.swipeRefresh.setColorSchemeResources(
            com.tradingpairs.asia.R.color.accent_primary
        )
        binding.swipeRefresh.setProgressBackgroundColorSchemeResource(
            com.tradingpairs.asia.R.color.surface
        )
    }

    private fun observeViewModel() {
        viewModel.pairsState.observe(viewLifecycleOwner) { pairs ->
            adapter.updateItems(pairs)
        }
        viewModel.isRefreshing.observe(viewLifecycleOwner) { refreshing ->
            binding.swipeRefresh.isRefreshing = refreshing
        }
        viewModel.lastUpdated.observe(viewLifecycleOwner) { time ->
            if (time.isNotEmpty()) {
                binding.tvLastUpdated.text = time
                binding.tvLastUpdated.visibility = View.VISIBLE
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
