package nz.co.test.transactions.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import nz.co.test.transactions.services.TransactionsRepository
import nz.co.test.transactions.services.TransactionsRepositoryImpl
import nz.co.test.transactions.services.TransactionsService

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {
    @Provides
    fun providesTransactionsRepository(service: TransactionsService): TransactionsRepository =
        TransactionsRepositoryImpl(service)

}