package nz.co.test.transactions.ui.transactiondetails

import androidx.lifecycle.SavedStateHandle
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
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import java.math.BigDecimal
import java.time.OffsetDateTime

@OptIn(ExperimentalCoroutinesApi::class)
class TransactionDetailsViewModelTest {
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
    fun `test fetchTransaction() returns list of transactions`() = runTest {
        val transaction = Transaction(
            id = 1,
            transactionDate = OffsetDateTime.now(),
            summary = "Summary",
            credit = BigDecimal.ZERO,
            debit = BigDecimal(50)
        )
        repository.transactions = listOf(transaction)

        val savedStateHandle = SavedStateHandle(mapOf("id" to 1))
        val viewModel = TransactionDetailsViewModel(repository, savedStateHandle)

        advanceUntilIdle()

        val state = viewModel.uiState.value
        assertTrue(state is TransactionDetailsUiState.Success)
        assertEquals(transaction, (state as TransactionDetailsUiState.Success).transaction)
    }

    @Test
    fun `test fetchTransaction() returns error when id is not found`() = runTest {
        val transaction = Transaction(
            id = 1,
            transactionDate = OffsetDateTime.now(),
            summary = "Summary",
            credit = BigDecimal.ZERO,
            debit = BigDecimal(50)
        )
        repository.transactions = listOf(transaction)

        val savedStateHandle = SavedStateHandle(mapOf("id" to 100)) //invalid id
        val viewModel = TransactionDetailsViewModel(repository, savedStateHandle)

        advanceUntilIdle()

        val state = viewModel.uiState.value
        assertTrue(state is TransactionDetailsUiState.Error)
    }

    @Test
    fun `test fetchTransaction() returns error when repository throws exception`() = runTest {
        repository.throwException = true
        val savedStateHandle = SavedStateHandle(mapOf("id" to 1))
        val viewModel = TransactionDetailsViewModel(repository, savedStateHandle)

        advanceUntilIdle()

        val state = viewModel.uiState.value
        assertTrue(state is TransactionDetailsUiState.Error)
    }
}