package com.xhstormr.app

import java.nio.charset.Charset
import java.util.stream.Collectors

enum class Extractor(val charsets: List<Charset>) {

    All(listOf()) {
        override fun extract(path: String) = values()
            .filterNot { it == All }
            .map { it.extract(path) }
            .flatMap { it.entries }
            .groupBy({ it.key }, { it.value.entries })
            .mapValues { (_, v) ->
                v
                    .flatten()
                    .groupBy({ it.key }, { it.value })
                    .mapValues { it.value.flatten().toSet() }
            }
    },

    EN(listOf(charset("GBK"), charset("UTF-16LE"))) {

        private val rulesFile = writeTempFile("""(?-u:[\w,.?:;'"/\(\)\-\\ ]){3,}""")

        override fun extract(path: String) = charsets.associateWith { charset ->
            readProcessOutput(COMMAND.format(rulesFile, charset, path))
                .parallelStream()
                // 至少包含一个词组
                .collect(
                    Collectors.groupingByConcurrent { str ->
                        when {
                            Dictionary.local_trie.matches(str) -> TextType.Local
                            Dictionary.website_trie.matches(str) -> TextType.Website
                            Dictionary.malicious_trie.matches(str) -> TextType.Malicious
                            Dictionary.malware_trie.matches(str) -> TextType.Malware
                            Dictionary.software_trie.matches(str) -> TextType.Software
                            Dictionary.antivirus_trie.matches(str) -> TextType.Antivirus
                            Dictionary.pinyin_word_trie.matches(str) -> TextType.PinyinWord
                            Dictionary.chinglish_words_trie.matches(str) -> TextType.ChinglishWords
                            Dictionary.chinglish_phrases_trie.matches(str) -> TextType.ChinglishPhrases
                            Dictionary.cet_trie.matches(str) -> TextType.CET
                            else -> TextType.None
                        }
                    }
                )
                .apply { remove(TextType.None) }
        }
    },

    ZH(listOf(charset("GBK"), charset("UTF-16LE"), charset("UTF-8"), charset("BIG5"))) {

        private val rulesFile = writeTempFile("""([\w.，、。？：；（） ]*)[\p{han}]{2,}([\w.，、。？：；（） ]*)""")

        override fun extract(path: String) = charsets.associateWith { charset ->
            readProcessOutput(COMMAND.format(rulesFile, charset, path))
                .parallelStream()
                // 只包含常用汉字
                .filter { str -> str.filter { it.isChinese() }.all { Dictionary.characters.contains(it) } }
                // 至少包含一个词组
                .collect(
                    Collectors.groupingByConcurrent { str ->
                        when {
                            Dictionary.local_trie.matches(str) -> TextType.Local
                            Dictionary.website_trie.matches(str) -> TextType.Website
                            Dictionary.malware_trie.matches(str) -> TextType.Malware
                            Dictionary.software_trie.matches(str) -> TextType.Software
                            Dictionary.antivirus_trie.matches(str) -> TextType.Antivirus
                            Dictionary.words_trie.matches(str) -> TextType.Words
                            else -> TextType.None
                        }
                    }
                )
                .apply { remove(TextType.None) }
        }
    },

    VulId(listOf(charset("GBK"), charset("UTF-16LE"))) {

        private val rulesFile by lazy { writeTempFile(Dictionary.vul_id) }

        override fun extract(path: String) = charsets.associateWith { charset ->
            readProcessOutput(COMMAND.format(rulesFile, charset, path))
                .run { mapOf(TextType.VulId to this) }
        }
    },

    Date(listOf(charset("GBK"), charset("UTF-16LE"), charset("UTF-8"), charset("BIG5"))) {

        private val rulesFile by lazy { writeTempFile(Dictionary.date) }

        override fun extract(path: String) = charsets.associateWith { charset ->
            readProcessOutput(COMMAND.format(rulesFile, charset, path))
                .run { mapOf(TextType.Date to this) }
        }
    },

    Domain(listOf(charset("GBK"), charset("UTF-16LE"), charset("UTF-8"), charset("BIG5"))) {

        private val rulesFile by lazy { writeTempFile(Dictionary.domain) }

        override fun extract(path: String) = charsets.associateWith { charset ->
            readProcessOutput(COMMAND.format(rulesFile, charset, path))
                .run { mapOf(TextType.Domain to this) }
        }
    };

    companion object {
        private const val COMMAND =
            """rg -a -o -f %s --encoding %s %s"""
    }

    abstract fun extract(path: String): Map<Charset, Map<TextType, Collection<String>>>
}

/*
* iconv -f GBK -t UTF-8 -c 123.dmp | rg "[\p{han}]{2,}" -a -o | busybox sort -u
*/

/*
* https://docs.rs/regex/
* https://perldoc.perl.org/perlre.html
* https://perldoc.perl.org/perluniprops.html
* https://www.pcre.org/current/doc/html/pcre2syntax.html
*/

/*
* https://www.iana.org/domains/root/db
* https://publicsuffix.org/list/public_suffix_list.dat
* https://en.m.wikipedia.org/wiki/List_of_Internet_top-level_domains
* https://github.com/hankcs/HanLP/blob/1.x/src/main/java/com/hankcs/hanlp/tokenizer/URLTokenizer.java
*/
