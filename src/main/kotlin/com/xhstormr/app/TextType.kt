package com.xhstormr.app

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = false)
enum class TextType {
    Antivirus,
    CET,
    ChinglishWords,
    ChinglishPhrases,
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
