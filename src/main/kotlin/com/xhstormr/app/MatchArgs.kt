package com.xhstormr.app

import java.nio.charset.Charset

data class MatchArgs(
    val path: String,
    val data: Map<Charset, List<String>>
)
