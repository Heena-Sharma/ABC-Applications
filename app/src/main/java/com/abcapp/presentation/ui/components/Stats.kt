package com.abcapp.presentation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.abcapp.R
import com.abcapp.data.model.Stats
import com.abcapp.presentation.ui.theme.Pink10
import com.abcapp.presentation.ui.funds.FundsViewModel

/**
 * Display stats of bottom sheet dialogue
 */
@Composable
fun StatsView(viewModel: FundsViewModel = hiltViewModel()) {
    val stats by produceState<Stats?>(initialValue = null, viewModel) {
        value = viewModel.getStats()
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Pink10)
    ) {
        stats?.let { safeStats ->
            Text(
                text = stringResource(R.string.items, safeStats.pageCount),
                modifier = Modifier
                    .padding(dimensionResource(id = R.dimen._10sdp))
                    .background(Pink10)
                    .fillMaxWidth()
                    .wrapContentHeight(Alignment.CenterVertically),
                fontSize = dimensionResource(id = R.dimen._16sdp).value.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                textAlign = TextAlign.Start
            )
            safeStats.top3CharsWithCounts.forEach {
                Text(
                    text = buildAnnotatedString {
                        append("• ")
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                            append("${it.key} = ${it.value}")
                        }
                    },
                    modifier = Modifier
                        .padding(dimensionResource(id = R.dimen._10sdp))
                        .background(Pink10)
                        .fillMaxWidth()
                        .wrapContentHeight(Alignment.CenterVertically),
                    fontSize = dimensionResource(id = R.dimen._14sdp).value.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.DarkGray,
                    textAlign = TextAlign.Start
                )
            }
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen._20sdp)))
        } ?: Text( 
            stringResource(R.string.no_data),
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(id = R.dimen._20sdp))
        )
    }
}