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

    val cet_trie = getSystemResourceAsStream("cet.txt")
        .bufferedReader()
        .readLines()
        .toSet()
        .associateBy { it }
        .let { AhoCorasickDoubleArrayTrie<String>().apply { build(it) } }

    val pinyin = getSystemResourceAsStream("pinyin.txt")
        .bufferedReader()
        .readLines()
        .filter { it.length > 2 }
        .toSet()

    val pinyin_word_trie = getSystemResourceAsStream("pinyin_word.txt")
        .bufferedReader()
        .readLines()
        .filter { it.length > 4 }
        .toSet()
        .associateBy { it }
        .let { AhoCorasickDoubleArrayTrie<String>().apply { build(it) } }

    val vul_number_trie = getSystemResourceAsStream("vul_number.txt")
        .bufferedReader()
        .readLines()
        .toSet()
        .associateBy { it }
        .let { AhoCorasickDoubleArrayTrie<String>().apply { build(it) } }

    val malicious_trie = getSystemResourceAsStream("malicious.txt")
        .bufferedReader()
        .readLines()
        .filter { it.length > 2 }
        .toSet()
        .associateBy { it }
        .let { AhoCorasickDoubleArrayTrie<String>().apply { build(it) } }

    val antivirus_trie = getSystemResourceAsStream("antivirus.txt")
        .bufferedReader()
        .readLines()
        .toSet()
        .associateBy { it }
        .let { AhoCorasickDoubleArrayTrie<String>().apply { build(it) } }

    val malware_trie = getSystemResourceAsStream("malware.txt")
        .bufferedReader()
        .readLines()
        .toSet()
        .associateBy { it }
        .let { AhoCorasickDoubleArrayTrie<String>().apply { build(it) } }

    val website_trie = getSystemResourceAsStream("website.txt")
        .bufferedReader()
        .readLines()
        .toSet()
        .associateBy { it }
        .let { AhoCorasickDoubleArrayTrie<String>().apply { build(it) } }

    val local_trie = getSystemResourceAsStream("local.txt")
        .bufferedReader()
        .readLines()
        .toSet()
        .associateBy { it }
        .let { AhoCorasickDoubleArrayTrie<String>().apply { build(it) } }

    val software_trie = getSystemResourceAsStream("software.txt")
        .bufferedReader()
        .readLines()
        .toSet()
        .associateBy { it }
        .let { AhoCorasickDoubleArrayTrie<String>().apply { build(it) } }

    val domain = getSystemResourceAsStream("domain.txt")
        .bufferedReader()
        .use { it.readText() }

    val date = getSystemResourceAsStream("date.txt")
        .bufferedReader()
        .use { it.readText() }
}
