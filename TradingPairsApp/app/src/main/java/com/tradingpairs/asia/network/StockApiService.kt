package com.tradingpairs.asia.network

import com.google.gson.Gson
import com.google.gson.JsonObject
import com.tradingpairs.asia.data.StockQuote
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import java.util.concurrent.TimeUnit

class StockApiService {

    private val client = OkHttpClient.Builder()
        .connectTimeout(15, TimeUnit.SECONDS)
        .readTimeout(15, TimeUnit.SECONDS)
        .build()

    private val gson = Gson()

    suspend fun fetchQuotes(symbols: List<String>): Map<String, StockQuote> = withContext(Dispatchers.IO) {
        val symbolsParam = symbols.joinToString(",")
        val url = "https://query1.finance.yahoo.com/v7/finance/quote?symbols=$symbolsParam"

        try {
            val request = Request.Builder()
                .url(url)
                .header("User-Agent", "Mozilla/5.0 (Linux; Android 13) AppleWebKit/537.36")
                .header("Accept", "application/json")
                .header("Accept-Language", "en-US,en;q=0.9")
                .build()

            val response = client.newCall(request).execute()
            if (!response.isSuccessful) return@withContext emptyMap()

            val body = response.body?.string() ?: return@withContext emptyMap()
            parseQuotes(body)
        } catch (e: Exception) {
            emptyMap()
        }
    }

    private fun parseQuotes(json: String): Map<String, StockQuote> {
        return try {
            val root = gson.fromJson(json, JsonObject::class.java)
            val results = root
                ?.getAsJsonObject("quoteResponse")
                ?.getAsJsonArray("result")
                ?: return emptyMap()

            val quotes = mutableMapOf<String, StockQuote>()
            for (item in results) {
                val obj = item.asJsonObject
                val symbol = obj.get("symbol")?.asString ?: continue
                val price = obj.get("regularMarketPrice")?.asDouble ?: 0.0
                val change = obj.get("regularMarketChange")?.asDouble ?: 0.0
                val changePct = obj.get("regularMarketChangePercent")?.asDouble ?: 0.0
                val currency = obj.get("currency")?.asString ?: "USD"
                val marketState = obj.get("marketState")?.asString ?: "CLOSED"

                quotes[symbol] = StockQuote(
                    symbol = symbol,
                    price = price,
                    change = change,
                    changePercent = changePct,
                    currency = currency,
                    marketState = marketState,
                    isLoaded = true
                )
            }
            quotes
        } catch (e: Exception) {
            emptyMap()
        }
    }
}
