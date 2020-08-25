package com.xhstormr.app

import com.hankcs.algorithm.AhoCorasickDoubleArrayTrie
import com.hankcs.hanlp.HanLP

object WhiteList {

    val domain = getSystemResourceAsStream("domain.txt")
        .bufferedReader()
        .use { it.readText() }

    val date = getSystemResourceAsStream("date.txt")
        .bufferedReader()
        .use { it.readText() }

    val characters_s = getSystemResourceAsStream("characters_s.txt")
        .bufferedReader()
        .use { it.readText() }

    val words_s = getSystemResourceAsStream("words_s.txt")
        .bufferedReader()
        .readLines()
        .toSet()

    val characters = (characters_s + HanLP.s2t(characters_s)).toSet()

    val words = words_s + words_s.map { HanLP.s2t(it) }

    val words_trie by lazy { buildDictTrie(words) }

    val cet_trie by lazy { buildDictTrie("cet.txt") }

    val pinyin_word_trie by lazy { buildDictTrie("pinyin_word.txt") }

    val vul_number_trie by lazy { buildDictTrie("vul_number.txt") }

    val malicious_trie by lazy { buildDictTrie("malicious.txt") }

    val antivirus_trie by lazy { buildDictTrie("antivirus.txt") }

    val malware_trie by lazy { buildDictTrie("malware.txt") }

    val website_trie by lazy { buildDictTrie("website.txt") }

    val local_trie by lazy { buildDictTrie("local.txt") }

    val software_trie by lazy { buildDictTrie("software.txt") }

    private fun buildDictTrie(name: String) = getSystemResourceAsStream(name)
        .bufferedReader()
        .readLines()
        .map { it.toLowerCase() }
        .toSet()
        .associateBy { it }
        .let { AhoCorasickDoubleArrayTrie<String>().apply { build(it) } }

    private fun buildDictTrie(list: Collection<String>) = list
        .map { it.toLowerCase() }
        .toSet()
        .associateBy { it }
        .let { AhoCorasickDoubleArrayTrie<String>().apply { build(it) } }
}
