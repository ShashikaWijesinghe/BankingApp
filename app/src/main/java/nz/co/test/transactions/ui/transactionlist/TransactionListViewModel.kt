package nz.co.test.transactions.ui.transactionlist

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
                TransactionListUiState.Success(transactions)
            } catch (e: Exception) {
                TransactionListUiState.Error
            }
        }
    }

}