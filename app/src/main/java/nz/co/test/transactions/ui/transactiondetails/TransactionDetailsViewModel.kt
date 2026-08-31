package nz.co.test.transactions.ui.transactiondetails

import android.util.Log
import androidx.lifecycle.SavedStateHandle
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
class TransactionDetailsViewModel @Inject constructor(
    private val repository: TransactionsRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    companion object {
        private val TAG = TransactionDetailsViewModel::class.simpleName
    }

    private val transactionId: Int = checkNotNull(savedStateHandle["id"])

    private val _uiState = MutableStateFlow<TransactionDetailsUiState>(TransactionDetailsUiState.Loading)
    val uiState: StateFlow<TransactionDetailsUiState> = _uiState.asStateFlow()

    init {
        fetchTransaction()
    }

    fun fetchTransaction() {
        viewModelScope.launch {
            _uiState.value = TransactionDetailsUiState.Loading
            _uiState.value = try {
                repository.getTransaction(transactionId)?.let {
                    TransactionDetailsUiState.Success(it)
                } ?: TransactionDetailsUiState.Error
            } catch (e: Exception) {
                Log.e(TAG, "fetchTransaction: errorMessage = ${e.message}")
                TransactionDetailsUiState.Error
            }
        }
    }
}