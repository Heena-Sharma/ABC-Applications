package com.abc.app.domain.usecases

import com.abc.app.data.model.Stats
import com.abc.app.data.model.Funds
import javax.inject.Inject

class GetStatsUseCase @Inject constructor() {
    operator fun invoke(funds: List<Funds>): Stats {
        val pageCount = funds.size
        val charCounts = funds.flatMap { project ->
            (project.title + project.shortDescription).lowercase().toList()
        }
            .filter { it.isLetter() }
            .groupBy { it }
            .mapValues { it.value.size }

        val top3CharsWithCounts = charCounts.entries.sortedByDescending { it.value }.take(3)
        return Stats(pageCount, top3CharsWithCounts.map { it.key }, top3CharsWithCounts)
    }
}