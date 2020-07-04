package com.xhstormr.app

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Message(
    val type: String,
    val data: Data
)

@JsonClass(generateAdapter = true)
data class Data(
    val path: Path,
    val lines: Lines,
    @field:Json(name = "line_number")
    val lineNumber: Long,
    @field:Json(name = "absolute_offset")
    val absoluteOffset: Long,
    val submatches: List<Submatch>
)

@JsonClass(generateAdapter = true)
data class Path(
    val text: String
)

@JsonClass(generateAdapter = true)
data class Lines(
    val bytes: String?,
    val text: String?
)

@JsonClass(generateAdapter = true)
data class Submatch(
    val match: Match,
    val start: Long,
    val end: Long
)

@JsonClass(generateAdapter = true)
data class Match(
    val bytes: String?,
    val text: String?
)

//

@JsonClass(generateAdapter = true)
data class Sample(
    val text: String,
    val offset: Long,
    val length: Long
)
