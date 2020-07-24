package com.xhstormr.app

object ChineseExtractor {

    private const val COMMAND =
        """cmd /c rg "[\p{han}]{2,}" -a -o --encoding %s %s"""

    fun extract(args: ExtractorArgs) = args.lang.charsets.associateWith { charset ->
        readProcessOutput(COMMAND.format(charset, args.path))
            // 只包含常用汉字
            .filter { str -> str.all { WhiteList.characters.contains(it) } }
            // 至少包含一个词组
            .filter { str ->
                WhiteList.words.any { str.contains(it) } ||
                    WhiteList.website.any { str.contains(it, true) } ||
                    WhiteList.malware.any { str.contains(it, true) } ||
                    WhiteList.antivirus.any { str.contains(it, true) }
            }
            .map { SearchArgs(args.path, it, charset) }
            .flatMap { Searcher.search(it) }
    }
}

/*
* iconv -f GBK -t UTF-8 -c 123.dmp | rg "[\p{han}]{2,}" -a -o | busybox sort -u
*/
