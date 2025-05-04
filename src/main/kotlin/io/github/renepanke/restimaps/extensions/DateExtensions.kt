package io.github.renepanke.restimaps.extensions

import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*

fun Date.toIso8601Utc(): String {
    return this.toInstant().atZone(ZoneId.of("UTC")).format(DateTimeFormatter.ISO_OFFSET_DATE_TIME)
}
