package com.shunk0616.gpshealthconnect.di

import com.shunk0616.gpshealthconnect.data.repository.AccountRepository
import com.shunk0616.gpshealthconnect.data.repository.GpsRepository
import com.shunk0616.gpshealthconnect.data.repository.LogRepository
import com.shunk0616.gpshealthconnect.data.repository.impl.AccountRepositoryImpl
import com.shunk0616.gpshealthconnect.data.repository.impl.LogRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun provideLogRepository(impl: LogRepositoryImpl): LogRepository

    @Binds
    abstract fun provideAccountRepository(impl: AccountRepositoryImpl): AccountRepository
}
