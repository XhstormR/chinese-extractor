package com.xhstormr.app

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = false)
enum class TextType {
    Antivirus,
    CET,
    Date,
    Domain,
    Local,
    Malicious,
    Malware,
    PinyinWord,
    Software,
    VulNumber,
    Website,
    Words,
    None;
}
