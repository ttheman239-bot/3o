package com.tradingpairs.asia.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.tradingpairs.asia.data.TradingPairsData
import com.tradingpairs.asia.databinding.BottomSheetPairDetailBinding

class PairDetailBottomSheet : BottomSheetDialogFragment() {

    private var _binding: BottomSheetPairDetailBinding? = null
    private val binding get() = _binding!!

    companion object {
        private const val ARG_PAIR_ID = "pair_id"
        fun newInstance(pairId: String) = PairDetailBottomSheet().apply {
            arguments = Bundle().apply { putString(ARG_PAIR_ID, pairId) }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = BottomSheetPairDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val pairId = arguments?.getString(ARG_PAIR_ID) ?: return
        val pair = TradingPairsData.pairs.find { it.id == pairId } ?: return

        binding.tvDetailTitle.text = "${pair.usSymbol} ↔ ${pair.hkSymbol}"
        binding.tvUsName.text = pair.usName
        binding.tvHkName.text = pair.hkName
        binding.tvRelationshipDetail.text = pair.relationship
        binding.tvCatalystDetail.text = pair.mainCatalyst
        binding.tvTimingDetail.text = pair.catalystSchedule
        binding.tvHkOpenDetail.text = "HK Market Open: ${pair.hkOpenTime}"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
