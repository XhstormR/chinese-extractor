package com.xhstormr.app

import java.util.stream.Collectors

object EnglishExtractor {

    private const val COMMAND =
        """cmd /c rg "(?-u:[\w,.?:;'/\(\)\-\"\\ ]){3,}" -a -o --encoding %s %s"""

    fun extract(args: ExtractorArgs) = args.type.charsets.associateWith { charset ->
        readProcessOutput(COMMAND.format(charset, args.path))
            .parallelStream()
            // 至少包含一个词组
            .collect(
                Collectors.groupingByConcurrent<String, TextType> { str ->
                    when {
                        WhiteList.cet_trie.matches(str) -> TextType.CET
                        WhiteList.website_trie.matches(str) -> TextType.Website
                        WhiteList.malware_trie.matches(str) -> TextType.Malware
                        WhiteList.malicious_trie.matches(str) -> TextType.Malicious
                        WhiteList.antivirus_trie.matches(str) -> TextType.Antivirus
                        WhiteList.vul_number_trie.matches(str) -> TextType.VulNumber
                        WhiteList.pinyin_word_trie.matches(str) -> TextType.PinyinWord
                        else -> TextType.None
                    }
                }
            )
            .apply { remove(TextType.None) }
    }
}
