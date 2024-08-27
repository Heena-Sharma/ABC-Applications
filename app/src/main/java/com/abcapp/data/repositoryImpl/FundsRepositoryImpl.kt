package com.abcapp.data.repositoryImpl

import com.abcapp.presentation.utils.dataSet
import com.abcapp.data.model.FundsOrg
import com.abcapp.domain.repository.FundsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class FundsRepositoryImpl @Inject constructor() : FundsRepository {

    override fun getFundingProjects(): Flow<List<FundsOrg>> = flowOf(dataSet)
}