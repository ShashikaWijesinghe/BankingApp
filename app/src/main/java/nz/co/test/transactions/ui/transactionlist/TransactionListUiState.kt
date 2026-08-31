package nz.co.test.transactions.ui.transactionlist

import nz.co.test.transactions.services.Transaction

sealed interface TransactionListUiState {
    data object Loading : TransactionListUiState
    data class Success(val transactions: List<Transaction>) : TransactionListUiState
    data object Error : TransactionListUiState
}
