package com.abc.app.domain.repository

import com.abc.app.data.model.FundsOrg
import kotlinx.coroutines.flow.Flow

interface FundsRepository {
    fun getFundingProjects(): Flow<List<FundsOrg>>
}