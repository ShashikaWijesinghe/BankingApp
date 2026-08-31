package nz.co.test.transactions.services

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TransactionsRepositoryImpl @Inject constructor(
    private val service: TransactionsService
) : TransactionsRepository {

    private var transactions: List<Transaction>? = null

    override suspend fun getTransactions(): List<Transaction> =
        transactions ?: service.retrieveTransactions().asList().also {
            transactions = it
        }.sortedByDescending {
            it.transactionDate
        }

    override suspend fun getTransaction(id: Int): Transaction? =
        getTransactions().find { it.id == id }

}

