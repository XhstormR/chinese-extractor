package com.xhstormr.app

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import java.nio.charset.Charset

object CharsetAdapter {

    @ToJson
    fun toJson(charset: Charset): String = charset.name()

    @FromJson
    fun fromJson(charset: String) = charset(charset)
}
