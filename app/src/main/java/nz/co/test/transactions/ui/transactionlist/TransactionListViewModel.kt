package nz.co.test.transactions.ui.transactionlist

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import nz.co.test.transactions.services.TransactionsRepository
import javax.inject.Inject

@HiltViewModel
class TransactionListViewModel @Inject constructor(
    private val repository: TransactionsRepository
) : ViewModel() {

    companion object {
        private val TAG = TransactionListViewModel::class.simpleName
    }

    private val _uiState = MutableStateFlow<TransactionListUiState>(TransactionListUiState.Loading)
    val uiState: StateFlow<TransactionListUiState> = _uiState.asStateFlow()

    init {
        fetchTransactions()
    }

    fun fetchTransactions() {
        viewModelScope.launch {
            _uiState.value = TransactionListUiState.Loading
            _uiState.value = try {
                val transactions = repository.getTransactions()
                Log.i(TAG, "fetchTransactions: transactions = ${transactions.size}")
                TransactionListUiState.Success(transactions)
            } catch (e: Exception) {
                Log.e(TAG, "fetchTransactions: errorMessage = ${e.message}")
                TransactionListUiState.Error
            }
        }
    }

}