package nz.co.test.transactions.ui.common

import android.icu.text.NumberFormat
import androidx.compose.ui.graphics.Color
import nz.co.test.transactions.services.Transaction
import nz.co.test.transactions.ui.theme.CreditColor
import nz.co.test.transactions.ui.theme.DebitColor
import java.math.BigDecimal
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

val transactionDateFormatter: DateTimeFormatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)

fun getAmount(transaction: Transaction): String {
    val amount = if (transaction.credit > BigDecimal.ZERO) {
        transaction.credit
    } else {
        transaction.debit
    }
    return NumberFormat.getCurrencyInstance().format(amount)
}

fun getAmountColor(transaction: Transaction): Color =
    if (transaction.credit > BigDecimal.ZERO) CreditColor else DebitColor