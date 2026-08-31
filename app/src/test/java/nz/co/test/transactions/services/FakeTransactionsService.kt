package nz.co.test.transactions.services

class FakeTransactionsService : TransactionsService {
    var transactions: Array<Transaction> = emptyArray()
    var throwException: Boolean = false

    override suspend fun retrieveTransactions(): Array<Transaction> {
        if (throwException) {
            throw Exception()
        }
        return transactions
    }
}