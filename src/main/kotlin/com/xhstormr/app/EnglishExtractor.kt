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
}
