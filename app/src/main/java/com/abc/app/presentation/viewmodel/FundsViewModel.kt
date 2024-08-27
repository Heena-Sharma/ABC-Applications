
package com.abc.app.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abc.app.data.model.Funds
import com.abc.app.data.model.FundsOrg
import com.abc.app.data.model.Stats
import com.abc.app.domain.common.Result
import com.abc.app.domain.usecases.GetFundsUseCase
import com.abc.app.domain.usecases.GetStatsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FundsViewModel @Inject constructor(
    private val getFundsUseCase: GetFundsUseCase,
    private val getStatsUseCase: GetStatsUseCase
) : ViewModel() {
    private val _fundsState = MutableStateFlow<Result<List<FundsOrg>>>(Result.Loading)
    val fundsState: StateFlow<Result<List<FundsOrg>>> = _fundsState.asStateFlow()
    private val _filteredFundsState = MutableStateFlow<Result<List<Funds>>>(Result.Loading)
    val filteredFundsState: StateFlow<Result<List<Funds>>> = _filteredFundsState.asStateFlow()
    private var currentPosition = 0

    init {
        loadFunds()
    }

    private fun loadFunds() = viewModelScope.launch {
        getFundsUseCase().collect { result ->
            _fundsState.value = result
            if (result is Result.Success && result.data.isNotEmpty()) {
                updateFilteredFunds(result.data[0].list)
            }
        }
    }

    fun getData(position: Int) = viewModelScope.launch {
        currentPosition = position
        val fundsResult = fundsState.value
        if (fundsResult is Result.Success && position in fundsResult.data.indices) {
            updateFilteredFunds(fundsResult.data[position].list)
        }
    }

    fun filterListData(searchText: String) = viewModelScope.launch {
        val fundsResult = fundsState.value
        if (fundsResult is Result.Success) {
            val filtered = if (searchText.isNotEmpty()) {
                fundsResult.data[currentPosition].list.filter { fund ->
                    fund.title.contains(searchText, ignoreCase = true)
                }
            } else {
                fundsResult.data[currentPosition].list
            }

            updateFilteredFunds(filtered)
        }
    }

    private fun updateFilteredFunds(funds: List<Funds>) {
        if (funds.isEmpty()) {
            _filteredFundsState.value = Result.Error("No record found")
        } else {
            _filteredFundsState.value = Result.Success(funds)
        }
    }

    fun getStatsForCurrentData(): Stats? {
        val currentFunds = (filteredFundsState.value as? Result.Success)?.data
        return currentFunds?.let { getStatsUseCase(it) }
    }
}