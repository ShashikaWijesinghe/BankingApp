package nz.co.test.transactions.ui.transactionlist

import junit.framework.Assert.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import nz.co.test.transactions.services.FakeTransactionsRepository
import nz.co.test.transactions.services.Transaction
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import java.math.BigDecimal
import java.time.OffsetDateTime

@OptIn(ExperimentalCoroutinesApi::class)
class TransactionListViewModelTest {
    private val testDispatcher = StandardTestDispatcher()
    private lateinit var repository: FakeTransactionsRepository

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        repository = FakeTransactionsRepository()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `test getTransactions() returns list of transactions`() = runTest {
        repository.transactions = listOf(
            Transaction(
                id = 1,
                transactionDate = OffsetDateTime.now(),
                summary = "Summary",
                credit = BigDecimal.ZERO,
                debit = BigDecimal(50)
            )
        )

        val viewModel = TransactionListViewModel(repository)

        advanceUntilIdle()

        val state = viewModel.uiState.value
        assertTrue(state is TransactionListUiState.Success)
        assertEquals(1, (state as TransactionListUiState.Success).transactions.size)
    }

    @Test
    fun `test getTransactions() returns error when repository throws exception`() = runTest {
        repository.throwException = true
        val viewModel = TransactionListViewModel(repository)

        advanceUntilIdle()

        val state = viewModel.uiState.value
        assertTrue(state is TransactionListUiState.Error)
    }
}