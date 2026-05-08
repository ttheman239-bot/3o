package com.tradingpairs.asia.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tradingpairs.asia.data.PairWithQuotes
import com.tradingpairs.asia.data.TradingPairsData
import com.tradingpairs.asia.network.StockApiService
import kotlinx.coroutines.launch

class PairsViewModel : ViewModel() {

    private val apiService = StockApiService()

    private val _pairsState = MutableLiveData<List<PairWithQuotes>>()
    val pairsState: LiveData<List<PairWithQuotes>> = _pairsState

    private val _isRefreshing = MutableLiveData(false)
    val isRefreshing: LiveData<Boolean> = _isRefreshing

    private val _lastUpdated = MutableLiveData<String>("")
    val lastUpdated: LiveData<String> = _lastUpdated

    init {
        loadInitialState()
        fetchPrices()
    }

    private fun loadInitialState() {
        _pairsState.value = TradingPairsData.pairs.map { pair ->
            PairWithQuotes(pair = pair, usQuote = null, hkQuote = null, isLoading = true)
        }
    }

    fun fetchPrices() {
        viewModelScope.launch {
            _isRefreshing.value = true

            val allSymbols = TradingPairsData.pairs.flatMap {
                listOf(it.usSymbol, it.hkSymbol)
            }

            val quotes = apiService.fetchQuotes(allSymbols)

            val updatedPairs = TradingPairsData.pairs.map { pair ->
                PairWithQuotes(
                    pair = pair,
                    usQuote = quotes[pair.usSymbol],
                    hkQuote = quotes[pair.hkSymbol],
                    isLoading = false,
                    error = if (quotes.isEmpty()) "Unable to fetch prices — check internet connection" else null
                )
            }

            _pairsState.value = updatedPairs
            _isRefreshing.value = false

            if (quotes.isNotEmpty()) {
                val now = java.text.SimpleDateFormat("HH:mm:ss", java.util.Locale.getDefault())
                    .format(java.util.Date())
                _lastUpdated.value = "Updated $now"
            }
        }
    }
}
