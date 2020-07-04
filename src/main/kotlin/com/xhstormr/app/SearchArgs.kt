package com.xhstormr.app

import java.nio.charset.Charset

data class SearchArgs(
    val path: String,
    val pattern: String,
    val charset: Charset
)