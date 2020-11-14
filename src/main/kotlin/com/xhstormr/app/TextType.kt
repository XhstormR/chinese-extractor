package com.xhstormr.app

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = false)
enum class TextType(val lexicon: String) {
    Antivirus("antivirus.txt"),
    CET("cet.txt"),
    Characters("characters_s.txt"),
    ChinglishWords("chinglish_words.txt"),
    ChinglishPhrases("chinglish_phrases.txt"),
    Date("date.txt"),
    Domain("domain.txt"),
    Local("local.txt"),
    Malicious("malicious.txt"),
    Malware("malware.txt"),
    PinyinWord("pinyin_word.txt"),
    Software("software.txt"),
    VulId("vul_id.txt"),
    Website("website.txt"),
    Words("words_s.txt"),
    None("");
}
