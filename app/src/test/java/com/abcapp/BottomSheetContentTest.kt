package com.abcapp

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.runtime.mutableStateOf
import com.abcapp.presentation.ui.components.BottomSheetContent
import com.abcapp.presentation.ui.funds.FundsViewModel
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.*

class BottomSheetContentTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun bottomSheetShowsWhenStateIsTrue() {
        val showBottomSheet = mutableStateOf(false)
        val mockViewModel = mock(FundsViewModel::class.java)

        composeTestRule.setContent {
            BottomSheetContent(showBottomSheet, viewModel = mockViewModel)
        }

        composeTestRule.onNodeWithTag("StatsBottomSheetDialog").assertDoesNotExist()

        showBottomSheet.value = true

        composeTestRule.onNodeWithTag("StatsBottomSheetDialog").assertIsDisplayed()
    }

    @Test
    fun bottomSheetHidesWhenDismissRequested() {
        val showBottomSheet = mutableStateOf(true)
        val mockViewModel = mock(FundsViewModel::class.java)

        composeTestRule.setContent {
            BottomSheetContent(showBottomSheet, viewModel = mockViewModel)
        }

        composeTestRule.onNodeWithTag("StatsBottomSheetDialog").assertIsDisplayed()

        composeTestRule.onNodeWithTag("StatsBottomSheetDialog")
            .performClick()

        composeTestRule.onNodeWithTag("StatsBottomSheetDialog").assertDoesNotExist()
    }

}