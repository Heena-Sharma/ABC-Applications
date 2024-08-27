package com.abcapp.domain.repository

import com.abcapp.data.model.FundsOrg
import kotlinx.coroutines.flow.Flow

interface FundsRepository {
    fun getFundingProjects(): Flow<List<FundsOrg>>
}