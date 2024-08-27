package com.abc.app.di

import com.abc.app.data.repositoryImpl.FundsRepositoryImpl
import com.abc.app.domain.repository.FundsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
abstract class FundsModule {
    @Binds
    abstract fun bindFundsRepository(
        impl: FundsRepositoryImpl
    ): FundsRepository
}