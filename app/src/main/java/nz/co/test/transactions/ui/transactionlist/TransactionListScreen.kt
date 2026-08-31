package nz.co.test.transactions.ui.transactionlist

import android.icu.text.NumberFormat
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import nz.co.test.transactions.R
import nz.co.test.transactions.services.Transaction
import nz.co.test.transactions.ui.theme.CreditColor
import nz.co.test.transactions.ui.theme.DebitColor
import java.math.BigDecimal
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransactionListScreen(
    navController: NavController,
    viewModel: TransactionListViewModel = hiltViewModel()
) {
    val defaultMargin = dimensionResource(R.dimen.default_margin)
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(R.string.transaction_list_title))
                }
            )
        }

    ) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            when (uiState) {
                TransactionListUiState.Loading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                is TransactionListUiState.Success -> {
                    val transactions = (uiState as TransactionListUiState.Success).transactions
                    LazyColumn {
                        items(transactions.size) { index ->
                            val transaction = transactions[index]
                            TransactionListItem(transaction)
                        }
                    }

                }

                is TransactionListUiState.Error -> {
                    Column(
                        modifier = Modifier.align(Alignment.Center),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = stringResource(R.string.loading_error_message))
                        Spacer(modifier = Modifier.height(defaultMargin))
                        Button(onClick = { viewModel.fetchTransactions() }) {
                            Text(text = stringResource(R.string.retry))
                        }
                    }

                }
            }
        }
    }
}

private val transactionDateFormatter: DateTimeFormatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)

@Composable
private fun TransactionListItem(
    transaction: Transaction
) {
    val smallMargin = dimensionResource(R.dimen.small_margin)
    val defaultMargin = dimensionResource(R.dimen.default_margin)

    Card(
        modifier = Modifier.fillMaxWidth()
            .padding(horizontal = defaultMargin, vertical = smallMargin)
    ) {
        Column (
            modifier = Modifier.padding(defaultMargin)
        ) {
            Text(text = transaction.transactionDate.format(transactionDateFormatter))
            Row {
                Text(
                    modifier = Modifier.weight(1f),
                    text = transaction.summary
                )
                Text(
                    text = getAmount(transaction),
                    fontWeight = FontWeight.Bold,
                    color = getAmountColor(transaction)
                )
            }
        }
    }
}

private fun getAmount(transaction: Transaction): String {
    val amount = if (transaction.credit > BigDecimal.ZERO) {
        transaction.credit
    } else {
        transaction.debit
    }
    return NumberFormat.getCurrencyInstance().format(amount)
}


private fun getAmountColor(transaction: Transaction): Color =
    if (transaction.credit > BigDecimal.ZERO) CreditColor else DebitColor