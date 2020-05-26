package com.xhstormr.app

object WhiteList {

    val words = getSystemResourceAsStream("words.txt")
        .bufferedReader()
        .readLines()
        .toSet()

    val characters = getSystemResourceAsStream("characters.txt")
        .bufferedReader()
        .use { it.readText() }
        .toCharArray()

    val cet = getSystemResourceAsStream("cet.txt")
        .bufferedReader()
        .readLines()
        .toSet()
}
