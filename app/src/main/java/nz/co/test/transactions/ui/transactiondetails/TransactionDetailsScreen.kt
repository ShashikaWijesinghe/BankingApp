package nz.co.test.transactions.ui.transactiondetails

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import nz.co.test.transactions.R
import nz.co.test.transactions.ui.common.getAmount
import nz.co.test.transactions.ui.common.getAmountColor
import nz.co.test.transactions.ui.common.transactionDateFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransactionDetailsScreen(
    viewModel: TransactionDetailsViewModel = hiltViewModel()
) {
    val smallMargin = dimensionResource(R.dimen.small_margin)
    val defaultMargin = dimensionResource(R.dimen.default_margin)
    val largeMargin = dimensionResource(R.dimen.large_margin)

    val uiState = viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(R.string.transaction_details_title))
                }
            )
        }

    ) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {

            when (uiState.value) {
                TransactionDetailsUiState.Loading -> {
                    CircularProgressIndicator(
                       modifier = Modifier.align(Alignment.Center)
                    )
                }

                is TransactionDetailsUiState.Success -> {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = defaultMargin, vertical = smallMargin)
                    ) {
                        val transaction = (uiState.value as TransactionDetailsUiState.Success).transaction
                        Column (
                            modifier = Modifier.fillMaxWidth().padding(largeMargin),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(text = transaction.transactionDate.format(transactionDateFormatter))
                            Spacer(modifier = Modifier.height(defaultMargin))
                            Text(text = transaction.summary)
                            Spacer(modifier = Modifier.height(defaultMargin))
                            Text(
                                text = getAmount(transaction),
                                fontWeight = FontWeight.Bold,
                                fontSize = MaterialTheme.typography.headlineMedium.fontSize,
                                color = getAmountColor(transaction)
                            )
                        }
                    }
                }

                is TransactionDetailsUiState.Error -> {
                    Column(
                        modifier = Modifier.align(Alignment.Center),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = stringResource(R.string.transaction_details_loading_error_message))
                        Spacer(modifier = Modifier.height(defaultMargin))
                        Button(onClick = { viewModel.fetchTransaction() }) {
                            Text(text = stringResource(R.string.retry))
                        }
                    }
                }
            }
        }
    }
}