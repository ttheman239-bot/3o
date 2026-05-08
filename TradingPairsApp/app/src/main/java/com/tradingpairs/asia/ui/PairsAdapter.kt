package com.tradingpairs.asia.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.tradingpairs.asia.R
import com.tradingpairs.asia.data.PairType
import com.tradingpairs.asia.data.PairWithQuotes
import com.tradingpairs.asia.databinding.ItemTradingPairBinding
import java.text.NumberFormat
import java.util.Locale

class PairsAdapter(
    private var items: List<PairWithQuotes> = emptyList(),
    private val onItemClick: (PairWithQuotes) -> Unit
) : RecyclerView.Adapter<PairsAdapter.PairViewHolder>() {

    fun updateItems(newItems: List<PairWithQuotes>) {
        items = newItems
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PairViewHolder {
        val binding = ItemTradingPairBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PairViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PairViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size

    inner class PairViewHolder(private val binding: ItemTradingPairBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: PairWithQuotes) {
            val ctx = binding.root.context
            val pair = item.pair

            binding.tvPairTitle.text = "${pair.usSymbol} ↔ ${pair.hkSymbol}"
            binding.tvPairSubtitle.text = "${pair.usName} / ${pair.hkName}"
            binding.tvRelationship.text = pair.mainCatalyst
            binding.tvCatalystSchedule.text = pair.catalystSchedule

            val badgeText = when (pair.pairType) {
                PairType.SUBSIDIARY -> "SUBSIDIARY"
                PairType.EXPOSURE -> "DIRECT EXPOSURE"
                PairType.COMPETITOR -> "COMPETITOR"
            }
            val badgeColor = when (pair.pairType) {
                PairType.SUBSIDIARY -> R.color.badge_subsidiary
                PairType.EXPOSURE -> R.color.badge_exposure
                PairType.COMPETITOR -> R.color.badge_competitor
            }
            binding.tvBadge.text = badgeText
            binding.tvBadge.backgroundTintList = android.content.res.ColorStateList.valueOf(
                ContextCompat.getColor(ctx, badgeColor)
            )

            if (item.isLoading) {
                binding.tvUsPrice.text = "Loading..."
                binding.tvUsChange.text = ""
                binding.tvHkPrice.text = "Loading..."
                binding.tvHkChange.text = ""
            } else {
                item.usQuote?.let { q ->
                    binding.tvUsPrice.text = formatPrice(q.price, q.currency)
                    binding.tvUsChange.text = formatChange(q.change, q.changePercent)
                    binding.tvUsChange.setTextColor(ContextCompat.getColor(ctx, if (q.change >= 0) R.color.price_up else R.color.price_down))
                } ?: run {
                    binding.tvUsPrice.text = "--"
                    binding.tvUsChange.text = "N/A"
                    binding.tvUsChange.setTextColor(ContextCompat.getColor(ctx, R.color.text_secondary))
                }

                item.hkQuote?.let { q ->
                    binding.tvHkPrice.text = formatPrice(q.price, q.currency)
                    binding.tvHkChange.text = formatChange(q.change, q.changePercent)
                    binding.tvHkChange.setTextColor(ContextCompat.getColor(ctx, if (q.change >= 0) R.color.price_up else R.color.price_down))
                } ?: run {
                    binding.tvHkPrice.text = "--"
                    binding.tvHkChange.text = "N/A"
                    binding.tvHkChange.setTextColor(ContextCompat.getColor(ctx, R.color.text_secondary))
                }
            }

            item.error?.let {
                binding.tvUsPrice.text = "–"
                binding.tvHkPrice.text = "–"
                binding.tvUsChange.text = ""
                binding.tvHkChange.text = ""
            }

            binding.root.setOnClickListener { onItemClick(item) }
        }

        private fun formatPrice(price: Double, currency: String): String {
            val formatter = NumberFormat.getNumberInstance(Locale.US)
            formatter.minimumFractionDigits = 2
            formatter.maximumFractionDigits = 2
            val symbol = when (currency) {
                "HKD" -> "HK$"
                "USD" -> "$"
                else -> "$"
            }
            return "$symbol${formatter.format(price)}"
        }

        private fun formatChange(change: Double, changePct: Double): String {
            val sign = if (change >= 0) "+" else ""
            val fmt = NumberFormat.getNumberInstance(Locale.US)
            fmt.minimumFractionDigits = 2
            fmt.maximumFractionDigits = 2
            val fmtPct = NumberFormat.getNumberInstance(Locale.US)
            fmtPct.minimumFractionDigits = 2
            fmtPct.maximumFractionDigits = 2
            return "$sign${fmt.format(change)} ($sign${fmtPct.format(changePct)}%)"
        }
    }
}
