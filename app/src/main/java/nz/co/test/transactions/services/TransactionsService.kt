package nz.co.test.transactions.services

import retrofit2.http.GET

interface TransactionsService {
    @GET("test-data.json")
    suspend fun retrieveTransactions(): Array<Transaction>
}

