package nz.co.test.transactions.services

import com.squareup.moshi.JsonClass
import java.math.BigDecimal
import java.time.OffsetDateTime

@JsonClass(generateAdapter = true)
data class Transaction(
    val id: Int,
    val transactionDate: OffsetDateTime,
    val summary: String,
    val debit: BigDecimal,
    val credit: BigDecimal
)