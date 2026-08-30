package nz.co.test.transactions.di.network

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import java.math.BigDecimal
import java.time.LocalDateTime
import java.time.OffsetDateTime
import java.time.ZoneOffset

class MoshiAdapters {

    @ToJson
    fun toJson(value: BigDecimal): Double = value.toDouble()

    @FromJson
    fun bigDecimalFromJson(value: Double): BigDecimal = BigDecimal.valueOf(value)

    @ToJson
    fun toJson(value: OffsetDateTime): String = value.toLocalDateTime().toString()

    @FromJson
    fun offsetDateTimeFromJson(value: String): OffsetDateTime =
        LocalDateTime.parse(value).atOffset(ZoneOffset.UTC)
}
