package com.abcapp.di

import com.abcapp.data.repositoryImpl.FundsRepositoryImpl
import com.abcapp.domain.repository.FundsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
abstract class FundsModule {
    @Binds
    abstract fun bindFundingProjectRepository(
        impl: FundsRepositoryImpl
    ): FundsRepository
}