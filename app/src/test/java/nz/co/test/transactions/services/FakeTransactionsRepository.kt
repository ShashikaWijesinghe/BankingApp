package nz.co.test.transactions.services

class FakeTransactionsRepository : TransactionsRepository {
    var transactions: List<Transaction> = emptyList()
    var throwException: Boolean = false

    override suspend fun getTransactions(): List<Transaction> {
        if (throwException) {
            throw Exception()
        }
        return transactions
    }

    override suspend fun getTransaction(id: Int): Transaction? {
        if (throwException) {
            throw Exception()
        }
        return transactions.find { it.id == id }
    }

}