package com.abcapp.presentation.ui.funds

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.abcapp.R
import com.abcapp.presentation.ui.components.BottomSheetContent
import com.abcapp.presentation.ui.components.CustomTopAppBar
import com.abcapp.presentation.ui.components.FloatingActionButton
import com.abcapp.presentation.ui.theme.ABCAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FundsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ABCAppTheme {
                val viewModel by viewModels<FundsViewModel>()
                val showBottomSheet = rememberSaveable { mutableStateOf(false) }
                Scaffold(topBar = { CustomTopAppBar(title =getString(R.string.app_name)) },
                    containerColor = Color.White,
                    floatingActionButton = {
                        FloatingActionButton { showBottomSheet.value = true }
                    }) { innerPadding ->
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    ) {
                        BottomSheetContent(showBottomSheet, viewModel)
                    }
                }
            }
        }
    }
}