package com.xhstormr.web.domain.model

data class ExtractorArgs(
    val path: String,
    val type: ExtractorType,
    val boundary: Boolean,
    val ignoreCase: Boolean,
)
