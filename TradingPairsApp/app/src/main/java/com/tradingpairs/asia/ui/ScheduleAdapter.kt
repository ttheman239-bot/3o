package com.tradingpairs.asia.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.tradingpairs.asia.R
import com.tradingpairs.asia.data.CatalystEvent
import com.tradingpairs.asia.data.CatalystType
import com.tradingpairs.asia.data.TradingPairsData
import com.tradingpairs.asia.databinding.ItemCatalystEventBinding

class ScheduleAdapter(private val events: List<CatalystEvent>) :
    RecyclerView.Adapter<ScheduleAdapter.EventViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val binding = ItemCatalystEventBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return EventViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        holder.bind(events[position])
    }

    override fun getItemCount() = events.size

    inner class EventViewHolder(private val binding: ItemCatalystEventBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(event: CatalystEvent) {
            val ctx = binding.root.context
            val pair = TradingPairsData.pairs.find { it.id == event.pairId }

            binding.tvEventTitle.text = event.title
            binding.tvEventDescription.text = event.description
            binding.tvEventTiming.text = "⏰ ${event.timing}"
            binding.tvEventPair.text = pair?.let { "${it.usSymbol} / ${it.hkSymbol}" } ?: ""

            val iconRes = when (event.type) {
                CatalystType.MONTHLY_DATA -> R.drawable.ic_chart
                CatalystType.MARKET_OPEN -> R.drawable.ic_clock
                CatalystType.EARNINGS -> R.drawable.ic_chart
                CatalystType.GENERAL_NEWS -> R.drawable.ic_news
            }
            binding.ivEventIcon.setImageResource(iconRes)

            val colorRes = when (event.type) {
                CatalystType.MONTHLY_DATA -> R.color.accent_primary
                CatalystType.MARKET_OPEN -> R.color.badge_subsidiary
                CatalystType.EARNINGS -> R.color.badge_exposure
                CatalystType.GENERAL_NEWS -> R.color.text_secondary
            }
            binding.ivEventIcon.setColorFilter(ContextCompat.getColor(ctx, colorRes))
        }
    }
}
