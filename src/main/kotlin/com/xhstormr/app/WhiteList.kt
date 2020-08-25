package com.xhstormr.app

import com.hankcs.algorithm.AhoCorasickDoubleArrayTrie

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

    val words_trie = words_s.toMutableSet()
        .apply { addAll(words_t.asIterable()) }
        .toSet()
        .associateBy { it }
        .let { AhoCorasickDoubleArrayTrie<String>().apply { build(it) } }

    val domain = getSystemResourceAsStream("domain.txt")
        .bufferedReader()
        .use { it.readText() }

    val date = getSystemResourceAsStream("date.txt")
        .bufferedReader()
        .use { it.readText() }

    val cet_trie = buildDictTrie("cet.txt")

    val pinyin_word_trie = buildDictTrie("pinyin_word.txt")

    val vul_number_trie = buildDictTrie("vul_number.txt")

    val malicious_trie = buildDictTrie("malicious.txt")

    val antivirus_trie = buildDictTrie("antivirus.txt")

    val malware_trie = buildDictTrie("malware.txt")

    val website_trie = buildDictTrie("website.txt")

    val local_trie = buildDictTrie("local.txt")

    val software_trie = buildDictTrie("software.txt")

    private fun buildDictTrie(name: String) = getSystemResourceAsStream(name)
        .bufferedReader()
        .readLines()
        .toSet()
        .map { it.toLowerCase() }
        .associateBy { it }
        .let { AhoCorasickDoubleArrayTrie<String>().apply { build(it) } }
}
