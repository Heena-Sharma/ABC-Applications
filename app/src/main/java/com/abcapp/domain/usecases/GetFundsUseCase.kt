package com.abcapp.domain.usecases

import com.abcapp.data.model.FundsOrg
import com.abcapp.domain.repository.FundsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import com.abcapp.domain.common.Result

class GetFundsUseCase@Inject constructor(private val repository: FundsRepository) {
    operator fun invoke(): Flow<Result<List<FundsOrg>>> =
        repository.getFundingProjects()
            .map<List<FundsOrg>, Result<List<FundsOrg>>> { Result.Success(it) }
            .catch { emit(Result.Error(it.toString())) }
            .onStart { emit(Result.Loading) }
}