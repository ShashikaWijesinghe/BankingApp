package nz.co.test.transactions.services

interface TransactionsRepository {
    suspend fun getTransactions(): List<Transaction>
    suspend fun getTransaction(id: Int): Transaction?
}