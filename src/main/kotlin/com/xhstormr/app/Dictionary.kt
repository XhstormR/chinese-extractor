package com.xhstormr.app

import com.hankcs.hanlp.HanLP
import java.io.InputStream

object Dictionary {

    val domain = getDictStream(TextType.Domain.lexicon)
        .bufferedReader()
        .use { it.readText() }

    val date = getDictStream(TextType.Date.lexicon)
        .bufferedReader()
        .use { it.readText() }

    val characters_s = getDictStream(TextType.Characters.lexicon)
        .bufferedReader()
        .use { it.readText() }

    val words_s = getDictStream(TextType.Words.lexicon)
        .bufferedReader()
        .readLines()
        .toSet()

    val vul_id = getDictStream(TextType.VulId.lexicon)
        .bufferedReader()
        .readLines()
        .map { App.regex(it) }
        .toSet()

    val characters = (characters_s + HanLP.s2t(characters_s)).toSet()

    val words = words_s + words_s.map { HanLP.s2t(it) }

    val words_trie by lazy { buildDictTrie(words) }

    val cet_trie by lazy { buildDictTrie(TextType.CET.lexicon) }

    val pinyin_word_trie by lazy { buildDictTrie(TextType.PinyinWord.lexicon) }

    val malicious_trie by lazy { buildDictTrie(TextType.Malicious.lexicon) }

    val antivirus_trie by lazy { buildDictTrie(TextType.Antivirus.lexicon) }

    val malware_trie by lazy { buildDictTrie(TextType.Malware.lexicon) }

    val website_trie by lazy { buildDictTrie(TextType.Website.lexicon) }

    val local_trie by lazy { buildDictTrie(TextType.Local.lexicon) }

    val software_trie by lazy { buildDictTrie(TextType.Software.lexicon) }

    val chinglish_words_trie by lazy { buildDictTrie(TextType.ChinglishWords.lexicon) }

    val chinglish_phrases_trie by lazy { buildDictTrie(TextType.ChinglishPhrases.lexicon) }

    private fun buildDictTrie(name: String, transform: ((String) -> String)? = null) = getDictStream(name)
        .bufferedReader()
        .readLines()
        .map { transform?.invoke(it) ?: it }
        .run { buildDictTrie(this) }

    private fun buildDictTrie(list: Collection<String>) = list
        .toSet()
        .associateBy { it }
        .let { MyAhoCorasickDoubleArrayTrie(it, App.boundary, App.ignoreCase) }

    private fun getDictStream(pathname: String): InputStream {
        val stream1 = getFileInputStream(getCurrentJar().resolveSibling("lexicon").resolve(pathname))
        val stream2 = getSystemResourceAsStream(pathname)
        return stream1?.let { stream1 + stream2 } ?: stream2
    }
}
