package com.example.common

import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter


fun formatTimestamp(timestamp: Long, formatPattern: String = "yyyy-MM-dd"): String {
    val instant = Instant.ofEpochMilli(timestamp)
    val localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault())
    val formatter = DateTimeFormatter.ofPattern(formatPattern)
    return localDateTime.format(formatter)
}