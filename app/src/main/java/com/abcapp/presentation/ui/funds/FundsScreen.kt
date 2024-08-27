package com.abcapp.presentation.ui.funds

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.abcapp.data.model.Funds
import com.abcapp.data.model.FundsOrg
import com.abcapp.domain.common.Result
import com.abcapp.presentation.ui.components.CarouselView
import com.abcapp.presentation.ui.components.ListViewUI
import com.abcapp.presentation.ui.components.SearchBar
import kotlinx.coroutines.launch

@OptIn(
    ExperimentalFoundationApi::class
)
@Composable
fun MainScreen(viewModel: FundsViewModel = hiltViewModel()) {
    val fundsState by viewModel.fundsState.collectAsState()
    val filteredFundsState by viewModel.filteredFundsState.collectAsState()
    val pagerState = rememberPagerState {
        (fundsState as? Result.Success)?.data?.size ?: 0
    }
    var showCarousel by rememberSaveable { mutableStateOf(true) }
    val scrollState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }


    LaunchedEffect(scrollState.firstVisibleItemIndex) {
        showCarousel = scrollState.firstVisibleItemIndex == 0
    }

    LaunchedEffect(pagerState.currentPage) {
        viewModel.setSelection(pagerState.currentPage)
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            AnimatedVisibility(visible = showCarousel) {
                when (fundsState) {
                    is Result.Success -> CarouselView(
                        pagerState,
                        (fundsState as Result.Success<List<FundsOrg>>).data
                    )

                    else -> {}
                }
            }

            SearchBar(onSearch = viewModel::filterListData)

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                state = scrollState
            ) {
                when (filteredFundsState) {
                    is Result.Success -> {
                        items((filteredFundsState as Result.Success<List<Funds>>).data) { item ->
                            ListViewUI(item)
                        }
                    }

                    is Result.Error -> {
                        coroutineScope.launch {
                            snackbarHostState.showSnackbar(
                                message = (filteredFundsState as Result.Error).exception,
                                duration = SnackbarDuration.Short
                            )
                        }

                    }

                    Result.Loading -> {
                        item {
                            Column(
                                modifier = Modifier.fillParentMaxSize(),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                CircularProgressIndicator()
                            }
                        }
                    }
                }
            }
        }
    }
}