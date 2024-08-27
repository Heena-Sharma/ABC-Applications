package com.abcapp.presentation.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.hilt.navigation.compose.hiltViewModel
import com.abcapp.presentation.ui.funds.MainScreen
import com.abcapp.presentation.ui.funds.FundsViewModel

@Composable
fun BottomSheetContent(
    showBottomSheet: MutableState<Boolean>,
    viewModel: FundsViewModel = hiltViewModel()
) {
    MainScreen(viewModel)
    if (showBottomSheet.value) {
        StatsBottomSheetDialog(
            onDismissRequest = { showBottomSheet.value = false }
        ) {
            StatsView(viewModel)
        }
    }
}
