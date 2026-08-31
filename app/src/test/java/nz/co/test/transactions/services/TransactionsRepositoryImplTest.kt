package nz.co.test.transactions.services

import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test
import java.math.BigDecimal
import java.time.OffsetDateTime

class TransactionsRepositoryImplTest {
    private lateinit var service: FakeTransactionsService
    private lateinit var repository: TransactionsRepositoryImpl

    @Before
    fun setup() {
        service = FakeTransactionsService()
        repository = TransactionsRepositoryImpl(service)
    }

    @Test
    fun `test getTransactions() returns list of transactions`() = runTest {
        service.transactions = arrayOf(
            createTransaction(1)
        )

        val transactions = repository.getTransactions()

        assertEquals(1, transactions.size)
        assertEquals(1, transactions[0].id)
    }

    @Test
    fun `test getTransactions() returns list of transactions sorted by date`() = runTest {
        service.transactions = arrayOf(
            createTransaction(1, OffsetDateTime.now().minusDays(2)),
            createTransaction(2, OffsetDateTime.now()),
        )

        val transactions = repository.getTransactions()

        assertEquals(2, transactions[0].id)
        assertEquals(1, transactions[1].id)
    }

    @Test
    fun `test getTransaction returns correct transaction by id`() = runTest {
        service.transactions = arrayOf(
            createTransaction(1),
            createTransaction(2),
        )

        val transaction = repository.getTransaction(2)
        assertEquals(2, transaction?.id)
    }

    @Test
    fun `test getTransaction returns null when transaction id is not found`() = runTest {
        service.transactions = arrayOf(
            createTransaction(1),
            createTransaction(2),
        )

        val transaction = repository.getTransaction(10)
        assertNull(transaction)
    }

    @Test
    fun `test getTransactions() propagates exception from service`() = runTest {
        service.throwException = true

        assertThrows(Exception::class.java) {
            runBlocking {
                repository.getTransactions()
            }
        }
    }

    private fun createTransaction(
        id: Int,
        transactionDate: OffsetDateTime = OffsetDateTime.now()
    ): Transaction {
        return Transaction(
            id = id,
            transactionDate = transactionDate,
            summary = "Summary",
            credit = BigDecimal.ZERO,
            debit = BigDecimal(50)
        )
    }

}