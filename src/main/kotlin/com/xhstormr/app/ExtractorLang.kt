package com.xhstormr.app

import java.nio.charset.Charset

enum class ExtractorLang(val charsets: List<Charset>) {
    ZH(listOf(charset("GBK"), charset("UTF-16LE"), charset("UTF-8"), charset("BIG5"))),
    EN(listOf(charset("GBK"), charset("UTF-16LE")));
}
