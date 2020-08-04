package com.xhstormr.app

object WhiteList {

    val characters = getSystemResourceAsStream("characters.txt")
        .bufferedReader()
        .use { it.readText() }
        .toCharArray()

    val words = getSystemResourceAsStream("words.txt")
        .bufferedReader()
        .readLines()
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
