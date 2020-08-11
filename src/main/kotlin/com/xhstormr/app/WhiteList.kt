package com.xhstormr.app

object WhiteList {

    val characters_s = getSystemResourceAsStream("characters_s.txt")
        .bufferedReader()
        .use { it.readText() }
        .toCharArray()

    val characters_t = getSystemResourceAsStream("characters_t.txt")
        .bufferedReader()
        .use { it.readText() }
        .toCharArray()

    val characters = characters_s.toMutableSet()
        .apply { addAll(characters_t.asIterable()) }
        .toCharArray()

    val words_s = getSystemResourceAsStream("words_s.txt")
        .bufferedReader()
        .readLines()
        .toSet()

    val words_t = getSystemResourceAsStream("words_t.txt")
        .bufferedReader()
        .readLines()
        .toSet()

    val words = words_s.toMutableSet()
        .apply { addAll(words_t.asIterable()) }
        .toSet()

    val cet = getSystemResourceAsStream("cet.txt")
        .bufferedReader()
        .readLines()
        .toSet()

    val pinyin = getSystemResourceAsStream("pinyin.txt")
        .bufferedReader()
        .readLines()
        .filter { it.length > 2 }
        .toSet()

    val pinyin_word = getSystemResourceAsStream("pinyin_word.txt")
        .bufferedReader()
        .readLines()
        .filter { it.length > 4 }
        .toSet()

    val vul_number = getSystemResourceAsStream("vul_number.txt")
        .bufferedReader()
        .readLines()
        .toSet()

    val malicious = getSystemResourceAsStream("malicious.txt")
        .bufferedReader()
        .readLines()
        .filter { it.length > 2 }
        .toSet()

    val antivirus = getSystemResourceAsStream("antivirus.txt")
        .bufferedReader()
        .readLines()
        .toSet()

    val malware = getSystemResourceAsStream("malware.txt")
        .bufferedReader()
        .readLines()
        .toSet()

    val website = getSystemResourceAsStream("website.txt")
        .bufferedReader()
        .readLines()
        .toSet()

    val domain = getSystemResourceAsStream("domain.txt")
        .bufferedReader()
        .use { it.readText() }
}
