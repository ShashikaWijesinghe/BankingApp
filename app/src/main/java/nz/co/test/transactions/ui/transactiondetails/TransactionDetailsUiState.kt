package nz.co.test.transactions.ui.transactiondetails

import nz.co.test.transactions.services.Transaction

sealed interface TransactionDetailsUiState {
    data object Loading : TransactionDetailsUiState
    data class Success(val transaction: Transaction) : TransactionDetailsUiState
    data object Error : TransactionDetailsUiState
}