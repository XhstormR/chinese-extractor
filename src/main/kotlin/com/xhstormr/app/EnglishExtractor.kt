package com.xhstormr.app

object EnglishExtractor {

    private const val COMMAND =
        """cmd /c rg "(?-u:[\w./\-\"\\ ]){2,}" -a -o --encoding %s %s"""

    fun extract(args: ExtractorArgs) = args.lang.charsets.associateWith { charset ->
        readProcessOutput(COMMAND.format(charset, args.path))
            // 至少包含一个词组
            .filter { str ->
                WhiteList.cet.any { str.contains(it, true) } ||
                    WhiteList.pinyin.any { str.contains(it, true) } ||
                    WhiteList.website.any { str.contains(it, true) } ||
                    WhiteList.malware.any { str.contains(it, true) } ||
                    WhiteList.malicious.any { str.contains(it, true) } ||
                    WhiteList.antivirus.any { str.contains(it, true) } ||
                    WhiteList.vul_number.any { str.contains(it, true) }
            }
    }
}
