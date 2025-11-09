package com.example.data.model.song


import java.util.regex.Pattern

data class LyricLine(
    val time: Long,
    val lyric: String
)
fun parseLrc(lrcText: String?): List<LyricLine> {
    if (lrcText.isNullOrBlank()) {
        return emptyList()
    }
    val lyricLines = mutableListOf<LyricLine>()
    val pattern = Pattern.compile("\\[(\\d{2}):(\\d{2})\\.(\\d{2,3})](.*)")

    lrcText.lines().forEach { line ->
        val matcher = pattern.matcher(line)
        if (matcher.matches()) {
            val minutes = matcher.group(1)?.toLong() ?: 0L
            val seconds = matcher.group(2)?.toLong() ?: 0L
            val millis = matcher.group(3)?.let {
                if (it.length == 3) it.toLong() else it.toLong() * 10
            } ?: 0L
            val text = matcher.group(4)?.trim() ?: ""

            val timeInMillis = minutes * 60 * 1000 + seconds * 1000 + millis
            if (text.isNotEmpty()) {
                lyricLines.add(LyricLine(timeInMillis, text))
            }
        }
    }
    return lyricLines.sortedBy { it.time }
}