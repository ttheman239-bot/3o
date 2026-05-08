package com.tradingpairs.asia.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.tradingpairs.asia.data.TradingPairsData
import com.tradingpairs.asia.databinding.FragmentScheduleBinding

class ScheduleFragment : Fragment() {

    private var _binding: FragmentScheduleBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentScheduleBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupScheduleList()
        updateCurrentTime()
    }

    private fun setupScheduleList() {
        val adapter = ScheduleAdapter(TradingPairsData.catalystEvents)
        binding.recyclerSchedule.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerSchedule.adapter = adapter
    }

    private fun updateCurrentTime() {
        val bkkFormat = java.text.SimpleDateFormat("dd MMM yyyy HH:mm", java.util.Locale.ENGLISH)
        bkkFormat.timeZone = java.util.TimeZone.getTimeZone("Asia/Bangkok")
        val hktFormat = java.text.SimpleDateFormat("HH:mm", java.util.Locale.ENGLISH)
        hktFormat.timeZone = java.util.TimeZone.getTimeZone("Asia/Hong_Kong")
        val nyFormat = java.text.SimpleDateFormat("HH:mm", java.util.Locale.ENGLISH)
        nyFormat.timeZone = java.util.TimeZone.getTimeZone("America/New_York")

        val now = java.util.Date()
        binding.tvCurrentBkk.text = "BKK: ${bkkFormat.format(now)}"
        binding.tvCurrentHkt.text = "HKT: ${hktFormat.format(now)}"
        binding.tvCurrentNy.text = "NY: ${nyFormat.format(now)}"

        val cal = java.util.Calendar.getInstance(java.util.TimeZone.getTimeZone("Asia/Hong_Kong"))
        val dayOfMonth = cal.get(java.util.Calendar.DAY_OF_MONTH)
        val monthName = java.text.SimpleDateFormat("MMMM", java.util.Locale.ENGLISH).format(now)

        binding.tvNextGgrInfo.text = buildString {
            append("Macau GGR: released 1st-2nd of each month\n")
            append("BYD Sales: released ~5th of each month\n")
            if (dayOfMonth <= 2) append("⚡ GGR data expected soon this month!")
            else if (dayOfMonth <= 5) append("⚡ BYD sales data expected soon!")
            else append("Next data: 1st of next month")
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
