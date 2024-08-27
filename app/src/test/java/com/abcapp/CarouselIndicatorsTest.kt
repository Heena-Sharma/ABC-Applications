import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.*
import com.abcapp.presentation.ui.components.CarouselIndicators
import org.junit.Rule
import org.junit.Test


class CarouselIndicatorsTest {
    @get:Rule
    val composeTestRule = createComposeRule()


    @Test
    fun highlightsCurrentPageIndicator() {
              composeTestRule.setContent {
            CarouselIndicators(pageCount = 3, currentPage = 1)
        }

        composeTestRule.onNodeWithTag("indicator", useUnmergedTree = true)
            .onChildAt(1)

    }
}