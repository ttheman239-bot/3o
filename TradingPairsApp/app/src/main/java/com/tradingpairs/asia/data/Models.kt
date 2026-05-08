package com.tradingpairs.asia.data

data class TradingPair(
    val id: String,
    val usSymbol: String,
    val usName: String,
    val hkSymbol: String,
    val hkName: String,
    val relationship: String,
    val mainCatalyst: String,
    val catalystSchedule: String,
    val hkOpenTime: String,
    val pairType: PairType
)

enum class PairType {
    SUBSIDIARY,      // YUM / 9987.HK
    EXPOSURE,        // WYNN / 1128.HK
    COMPETITOR       // TSLA / 1211.HK
}

data class StockQuote(
    val symbol: String,
    val price: Double,
    val change: Double,
    val changePercent: Double,
    val currency: String,
    val marketState: String = "REGULAR",
    val isLoaded: Boolean = false
)

data class PairWithQuotes(
    val pair: TradingPair,
    val usQuote: StockQuote?,
    val hkQuote: StockQuote?,
    val isLoading: Boolean = false,
    val error: String? = null
)

data class CatalystEvent(
    val pairId: String,
    val title: String,
    val description: String,
    val timing: String,
    val type: CatalystType
)

enum class CatalystType {
    MONTHLY_DATA,
    MARKET_OPEN,
    EARNINGS,
    GENERAL_NEWS
}

object TradingPairsData {
    val pairs = listOf(
        TradingPair(
            id = "yum_9987",
            usSymbol = "YUM",
            usName = "Yum! Brands",
            hkSymbol = "9987.HK",
            hkName = "Yum China (百胜中国)",
            relationship = "Subsidiary — Yum China is the exclusive operator of KFC & Pizza Hut in China, spun off from Yum! Brands in 2016",
            mainCatalyst = "China consumer data, KFC/Pizza Hut quarterly results",
            catalystSchedule = "Quarterly earnings + HK opens 8:30 BKK time",
            hkOpenTime = "08:30 BKK (09:30 HKT)",
            pairType = PairType.SUBSIDIARY
        ),
        TradingPair(
            id = "wynn_1128",
            usSymbol = "WYNN",
            usName = "Wynn Resorts",
            hkSymbol = "1128.HK",
            hkName = "Wynn Macau (永利澳門)",
            relationship = "Direct Exposure — Wynn Macau is Wynn Resorts' primary revenue engine, listed separately on HKEX",
            mainCatalyst = "Macau GGR (Gross Gaming Revenue) — released 1st-2nd of every month",
            catalystSchedule = "Monthly GGR data on 1st-2nd of month",
            hkOpenTime = "08:30 BKK (09:30 HKT)",
            pairType = PairType.EXPOSURE
        ),
        TradingPair(
            id = "tsla_1211",
            usSymbol = "TSLA",
            usName = "Tesla Inc.",
            hkSymbol = "1211.HK",
            hkName = "BYD Company (比亚迪)",
            relationship = "Direct Competitor — Both are global EV leaders; BYD surpassed Tesla in EV sales; often move inversely on share gain news",
            mainCatalyst = "BYD monthly sales report, EV policy news, China NEV subsidies",
            catalystSchedule = "BYD sales ~5th of month + any EV policy news",
            hkOpenTime = "08:30 BKK (09:30 HKT)",
            pairType = PairType.COMPETITOR
        )
    )

    val catalystEvents = listOf(
        CatalystEvent(
            pairId = "wynn_1128",
            title = "Macau GGR Monthly",
            description = "Gaming Inspection and Coordination Bureau releases monthly Gross Gaming Revenue data for all 6 Macau casinos",
            timing = "1st-2nd of every month, early morning",
            type = CatalystType.MONTHLY_DATA
        ),
        CatalystEvent(
            pairId = "tsla_1211",
            title = "BYD Monthly Sales",
            description = "BYD releases total NEV (New Energy Vehicle) delivery data including pure EV and plug-in hybrid",
            timing = "~5th of every month",
            type = CatalystType.MONTHLY_DATA
        ),
        CatalystEvent(
            pairId = "yum_9987",
            title = "HK Market Opens",
            description = "Hong Kong Stock Exchange opens. Best time to trade 9987.HK based on US session close signals",
            timing = "08:30 Bangkok time (09:30 HKT) daily",
            type = CatalystType.MARKET_OPEN
        ),
        CatalystEvent(
            pairId = "yum_9987",
            title = "China Consumer Data",
            description = "NBS releases retail sales, consumer confidence index — key drivers for Yum China performance",
            timing = "Monthly, mid-month (15th-16th typically)",
            type = CatalystType.MONTHLY_DATA
        )
    )
}
