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
                        WhiteList.cet.any { str.contains(it, true) } -> TextType.CET
                        WhiteList.website.any { str.contains(it, true) } -> TextType.Website
                        WhiteList.malware.any { str.contains(it, true) } -> TextType.Malware
                        WhiteList.malicious.any { str.contains(it, true) } -> TextType.Malicious
                        WhiteList.antivirus.any { str.contains(it, true) } -> TextType.Antivirus
                        WhiteList.vul_number.any { str.contains(it, true) } -> TextType.VulNumber
                        WhiteList.pinyin_word.any { str.contains(it, true) } -> TextType.PinyinWord
                        else -> TextType.None
                    }
                }
            )
            .apply { remove(TextType.None) }
    }
}
