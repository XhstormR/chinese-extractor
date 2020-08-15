package com.xhstormr.app

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = false)
enum class TextType {
    Antivirus,
    CET,
    Domain,
    Malicious,
    Malware,
    PinyinWord,
    VulNumber,
    Website,
    Words,
    None;
}
