package com.abc.app.domain.usecases

import com.abc.app.data.model.FundsOrg
import com.abc.app.domain.repository.FundsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import com.abc.app.domain.common.Result
import javax.inject.Inject

class GetFundsUseCase@Inject constructor(private val repository: FundsRepository) {
    operator fun invoke(): Flow<Result<List<FundsOrg>>> =
        repository.getFundingProjects()
            .map<List<FundsOrg>, Result<List<FundsOrg>>> { Result.Success(it) }
            .catch { emit(Result.Error(it.toString())) }
            .onStart { emit(Result.Loading) }
}