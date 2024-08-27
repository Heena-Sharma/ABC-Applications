package com.abc.app.data.repositoryImpl

import com.abc.app.presentation.utils.dataSet
import com.abc.app.data.model.FundsOrg
import com.abc.app.domain.repository.FundsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class FundsRepositoryImpl @Inject constructor() : FundsRepository {

    override fun getFundingProjects(): Flow<List<FundsOrg>> = flowOf(dataSet)
}