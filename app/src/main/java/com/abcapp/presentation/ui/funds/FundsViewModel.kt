package com.abcapp.presentation.ui.funds

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abcapp.R
import com.abcapp.data.model.Funds
import com.abcapp.data.model.FundsOrg
import com.abcapp.data.model.Stats
import com.abcapp.domain.common.Result
import com.abcapp.domain.common.Result.Error
import com.abcapp.domain.common.Result.Success
import com.abcapp.domain.usecases.GetFundsUseCase
import com.abcapp.domain.usecases.GetStatsUseCase
import com.abcapp.presentation.utils.StringProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FundsViewModel @Inject constructor(
    private val getFundsUseCase: GetFundsUseCase,
    private val getStatsUseCase: GetStatsUseCase,
    private val stringProvider: StringProvider
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
            if (result is Success && result.data.isNotEmpty()) {
                updateFilteredFunds(result.data[0].list)
            }
        }
    }

    private fun updateFilteredFunds(funds: List<Funds>) {
        _filteredFundsState.value = if (funds.isEmpty()) {
            Error(stringProvider.getString(R.string.no_data))
        } else{
            Success(funds)
        }
    }

    fun filterListData(searchText: String) = viewModelScope.launch {
        val fundsResult = fundsState.value
        if (fundsResult is Success) {
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

    fun setSelection(position: Int) {
        currentPosition = position
        viewModelScope.launch {
            val fundsResult = fundsState.value
            if (fundsResult is Success && position in fundsResult.data.indices) {
                updateFilteredFunds(fundsResult.data[position].list)
            }
        }
    }

    fun getStats(): Stats? {
        val currentFunds = (filteredFundsState.value as? Success)?.data
        return currentFunds?.let { getStatsUseCase(it) }
    }
}