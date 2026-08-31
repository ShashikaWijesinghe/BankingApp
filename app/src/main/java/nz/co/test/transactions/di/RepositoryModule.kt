package nz.co.test.transactions.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import nz.co.test.transactions.services.TransactionsRepository
import nz.co.test.transactions.services.TransactionsRepositoryImpl
import nz.co.test.transactions.services.TransactionsService
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    @Singleton
    fun providesTransactionsRepository(service: TransactionsService): TransactionsRepository =
        TransactionsRepositoryImpl(service)

}