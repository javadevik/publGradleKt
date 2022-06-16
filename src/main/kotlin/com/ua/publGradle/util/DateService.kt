package com.ua.publGradle.util

import org.springframework.stereotype.Component
import java.time.Clock
import java.time.ZoneId
import java.time.ZonedDateTime

@Component
class DateService(
    private val clock: Clock
) {

    fun getCurrentTimeByTimezone(zoneId: String): Long {
        val zonedDateTime = ZonedDateTime.now(ZoneId.of(zoneId))
        return zonedDateTime.toInstant().toEpochMilli()
    }

    fun getCurrentTime(): Long {
        return clock.millis()
    }

    fun isClientTimeLess(clientTime: Long, zoneId: String): Boolean {
        val currentTime = getCurrentTimeByTimezone(zoneId)
        return currentTime <= clientTime
    }
}