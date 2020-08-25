package com.xhstormr.app

import com.hankcs.hanlp.utility.TextUtility
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

        private val command =
            """cmd /c rg "(?-u:[\w,.?:;'/\(\)\-\"\\ ]){3,}" -a -o --encoding %s %s"""

        override fun extract(path: String) = charsets.associateWith { charset ->
            readProcessOutput(command.format(charset, path))
                .parallelStream()
                // 至少包含一个词组
                .collect(
                    Collectors.groupingByConcurrent { str ->
                        with(str.toLowerCase()) {
                            when {
                                WhiteList.cet_trie.matches(this) -> TextType.CET
                                WhiteList.local_trie.matches(this) -> TextType.Local
                                WhiteList.website_trie.matches(this) -> TextType.Website
                                WhiteList.malware_trie.matches(this) -> TextType.Malware
                                WhiteList.software_trie.matches(this) -> TextType.Software
                                WhiteList.malicious_trie.matches(this) -> TextType.Malicious
                                WhiteList.antivirus_trie.matches(this) -> TextType.Antivirus
                                WhiteList.vul_number_trie.matches(this) -> TextType.VulNumber
                                WhiteList.pinyin_word_trie.matches(this) -> TextType.PinyinWord
                                else -> TextType.None
                            }
                        }
                    }
                )
                .apply { remove(TextType.None) }
        }
    },

    ZH(listOf(charset("GBK"), charset("UTF-16LE"), charset("UTF-8"), charset("BIG5"))) {

        private val command =
            """cmd /c rg "([\w.，、。？：；（） ]*)[\p{han}]{2,}([\w.，、。？：；（） ]*)" -a -o --encoding %s %s"""

        override fun extract(path: String) = charsets.associateWith { charset ->
            readProcessOutput(command.format(charset, path))
                .parallelStream()
                // 只包含常用汉字
                .filter { str -> str.filter { TextUtility.isChinese(it) }.all { WhiteList.characters.contains(it) } }
                // 至少包含一个词组
                .collect(
                    Collectors.groupingByConcurrent { str ->
                        when {
                            WhiteList.local_trie.matches(str) -> TextType.Local
                            WhiteList.words_trie.matches(str) -> TextType.Words
                            WhiteList.website_trie.matches(str) -> TextType.Website
                            WhiteList.malware_trie.matches(str) -> TextType.Malware
                            WhiteList.software_trie.matches(str) -> TextType.Software
                            WhiteList.antivirus_trie.matches(str) -> TextType.Antivirus
                            else -> TextType.None
                        }
                    }
                )
                .apply { remove(TextType.None) }
        }
    },

    Date(listOf(charset("GBK"), charset("UTF-16LE"), charset("UTF-8"), charset("BIG5"))) {

        private val command =
            """cmd /c rg -a -o -f %s --encoding %s %s"""

        private val rulesFile = createTempFile()
            .apply { deleteOnExit() }
            .apply { writeText(WhiteList.date) }

        override fun extract(path: String) = charsets.associateWith { charset ->
            readProcessOutput(command.format(rulesFile, charset, path))
                .run { mapOf(TextType.Date to this) }
        }
    },

    Domain(listOf(charset("GBK"), charset("UTF-16LE"), charset("UTF-8"), charset("BIG5"))) {

        private val command =
            """cmd /c rg -a -o -f %s --encoding %s %s"""

        private val rulesFile = createTempFile()
            .apply { deleteOnExit() }
            .apply { writeText(WhiteList.domain) }

        override fun extract(path: String) = charsets.associateWith { charset ->
            readProcessOutput(command.format(rulesFile, charset, path))
                .run { mapOf(TextType.Domain to this) }
        }
    };

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
