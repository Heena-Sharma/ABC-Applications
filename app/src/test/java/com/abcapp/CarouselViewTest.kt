package com.abcapp

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onChildAt
import androidx.compose.ui.test.onNodeWithTag
import com.abcapp.data.model.FundsOrg
import com.abcapp.presentation.ui.components.CarouselView
import com.google.accompanist.pager.ExperimentalPagerApi
import kotlinx.coroutines.launch
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock

class CarouselViewTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @OptIn(ExperimentalFoundationApi::class)
    @Test
    fun displaysCorrectNumberOfCards() {
        val mockDataList = listOf(
            mock(FundsOrg::class.java),
            mock(FundsOrg::class.java),
            mock(FundsOrg::class.java)
        )
        composeTestRule.setContent {
            val pagerState = rememberPagerState(pageCount = { 2 })
            CarouselView(pagerState = pagerState, carouselListData = mockDataList)
        }

        composeTestRule.onAllNodesWithTag("card").assertCountEquals(mockDataList.size)
    }

    @OptIn(ExperimentalFoundationApi::class, ExperimentalPagerApi::class)
    @Test
    fun displaysCarouselIndicators() {
        val mockDataList = listOf(mock(FundsOrg::class.java))
        composeTestRule.setContent {
            val pagerState = rememberPagerState(pageCount = {2})
            CarouselView(pagerState = pagerState, carouselListData = mockDataList)
        }

        composeTestRule.onNodeWithTag("indicator").assertIsDisplayed()
    }

    @OptIn(ExperimentalFoundationApi::class)@Test
    fun scrollsToNextPage() {
        val mockDataList = listOf(
            mock(FundsOrg::class.java),
            mock(FundsOrg::class.java)
        )
        composeTestRule.setContent {
            val pagerState = rememberPagerState(pageCount = { 2 })
            val coroutineScope = rememberCoroutineScope()
            CarouselView(pagerState = pagerState, carouselListData = mockDataList)


            coroutineScope.launch {
                pagerState.animateScrollToPage(1)
            }
        }

        composeTestRule.mainClock.advanceTimeBy(500)

          composeTestRule.onNodeWithTag("indicator", useUnmergedTree = true)
            .onChildAt(1)
    }

}
