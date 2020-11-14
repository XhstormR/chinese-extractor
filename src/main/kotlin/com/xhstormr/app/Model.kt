package com.xhstormr.app

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Message(
    val type: String,
    val data: Data
)

@Serializable
data class Data(
    val path: Path,
    val lines: Lines,
    @SerialName("line_number")
    val lineNumber: Long,
    @SerialName("absolute_offset")
    val absoluteOffset: Long,
    val submatches: List<Submatch>
)

@Serializable
data class Path(
    val text: String
)

@Serializable
data class Lines(
    val bytes: String? = null,
    val text: String? = null
)

@Serializable
data class Submatch(
    val match: Match,
    val start: Long,
    val end: Long
)

@Serializable
data class Match(
    val bytes: String? = null,
    val text: String? = null
)

//

@Serializable
data class Sample(
    val text: String,
    val offset: Long,
    val length: Long
)
