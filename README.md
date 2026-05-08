# Asia Trading Pairs — Android App

แอป Android สำหรับติดตาม **3 คู่ trading pairs** ที่มี fundamental relationship ระหว่างหุ้น Asia (HK) ↔ US

---

## 📱 Download APK

**ลิ้งดาวน์โหลดล่าสุด:**
👉 [**Releases page**](../../releases/latest) — ดาวน์โหลด `AsiaTradingPairs-v1.0-debug.apk`

> Build status: CI จะ build APK อัตโนมัติทุกครั้งที่มีการ push code

---

## 3 คู่ที่ Focus

| คู่ | ความสัมพันธ์ | Catalyst หลัก | เวลาสำคัญ |
|-----|------------|--------------|----------|
| **YUM ↔ 9987.HK** | Subsidiary | China consumer data, KFC/PH news | HK เปิด 8:30 BKK |
| **WYNN ↔ 1128.HK** | Direct Exposure | Macau GGR วันที่ 1-2 | ต้นเดือนทุกเดือน |
| **TSLA ↔ 1211.HK** | Competitor | BYD monthly sales, EV news | ~วันที่ 5 ทุกเดือน |

---

## Features

- **Live Prices** — ดึงราคาจริงจาก Yahoo Finance API (pull-to-refresh)
- **Catalyst Schedule** — ตารางกิจกรรมที่ market-moving สำหรับแต่ละคู่
- **Multi-Timezone Clock** — Bangkok / Hong Kong / New York
- **Pair Detail** — กด card เพื่อดูรายละเอียด fundamental ของแต่ละคู่
- **Trading Guide** — คู่มือกลยุทธ์ Asia → US

---

## วิธีติดตั้ง

1. ดาวน์โหลด APK จาก [Releases](../../releases/latest)
2. เปิด **Settings → Security → Install unknown apps** → Allow
3. เปิดไฟล์ `.apk` แล้ว Install
4. เปิดแอป **"Asia Trading Pairs"**

> ต้องการ Android 8.0+ (API 26+)

---

## Tech Stack

- **Language:** Kotlin
- **UI:** Material 3, ViewBinding, RecyclerView
- **Architecture:** ViewModel + LiveData
- **Network:** OkHttp + Gson → Yahoo Finance API
- **Build:** Gradle 8.2, AGP 8.2.2, GitHub Actions CI

---

## Build Locally

```bash
git clone https://github.com/ttheman239-bot/3o.git
cd 3o/TradingPairsApp
gradle assembleDebug
# APK: app/build/outputs/apk/debug/app-debug.apk
```
