package com.tradingpairs.asia.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.tradingpairs.asia.databinding.FragmentInfoBinding

class InfoFragment : Fragment() {

    private var _binding: FragmentInfoBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupContent()
    }

    private fun setupContent() {
        binding.tvStrategyOverview.text = """
กลยุทธ์ Asia → US Trading Pairs

เทรดตามข่าวเอเชีย (HK) ไปสู่ตลาด US โดยใช้ความสัมพันธ์ fundamental ที่ชัดเจนระหว่างหุ้นทั้งสองฝั่ง

วิธีเล่น:
• ถ้า HK เปิดตลาดแล้วหุ้นฝั่ง HK วิ่งขึ้น → คาด US counterpart จะขึ้นตามตอน NY เปิด
• ถ้า HK ลง → คาด US ลงตาม (สำหรับ subsidiary/exposure pairs)
• สำหรับ TSLA/BYD: มักเคลื่อนไปทิศทางตรงข้าม (competition pair)

เวลาสำคัญ (Bangkok time):
• 08:30 — HK ตลาดเปิด (ช่วงที่ดีที่สุดสำหรับ pair 1 & 2)
• 21:30 — NY ตลาดเปิด (สัญญาณจาก HK สะท้อนมาที่นี่)
• 04:00 — NY ตลาดปิด
        """.trimIndent()

        binding.tvPair1Info.text = """
YUM ↔ 9987.HK (Yum China)
ความสัมพันธ์: Subsidiary
Yum China ถูก spin-off จาก Yum! Brands ปี 2016 เป็น exclusive operator ของ KFC, Pizza Hut, Taco Bell ในจีน ซึ่งมีร้านกว่า 14,000 แห่ง

สิ่งที่ต้องจับตา:
• China consumer confidence index
• Same-store sales growth quarterly
• RMB/USD exchange rate
• Food cost inflation in China
        """.trimIndent()

        binding.tvPair2Info.text = """
WYNN ↔ 1128.HK (Wynn Macau)
ความสัมพันธ์: Direct Revenue Exposure
Wynn Macau สร้างรายได้ประมาณ 70-75% ของ Wynn Resorts ทั้งหมด จดทะเบียนแยกใน HKEX

Catalyst หลัก (MONTHLY):
• Macau GGR (Gross Gaming Revenue) — ออกวันที่ 1-2 ของทุกเดือน
• ตัวเลขนี้ชี้วัดสุขภาพของ casino ทั้งมาเก๊า
• ถ้า GGR ดีกว่าคาด → ทั้ง 1128.HK และ WYNN มักขึ้น
        """.trimIndent()

        binding.tvPair3Info.text = """
TSLA ↔ 1211.HK (BYD)
ความสัมพันธ์: Direct Competitor
BYD แซง Tesla ในยอดขาย EV รวมตั้งแต่ปี 2022 ทั้งสองมีแรงขับเคลื่อนร่วมกัน (EV sector) แต่มักแย่งส่วนแบ่งตลาดกัน

Catalyst หลัก:
• BYD monthly delivery report (~วันที่ 5 ของเดือน)
• China NEV subsidy policy news
• Tesla quarterly delivery vs estimates
• ราคา lithium/battery materials

Note: คู่นี้ high volatility สูงสุด ระวัง gap risk
        """.trimIndent()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
