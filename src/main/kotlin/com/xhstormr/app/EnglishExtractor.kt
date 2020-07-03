package com.xhstormr.app

object EnglishExtractor {

    private const val COMMAND =
        """cmd /c rg "(?-u:[\w./\-\"\\ ]){2,}" -a -o --encoding %s %s"""

    fun extract(args: ExtractorArgs) = readProcessOutput(COMMAND.format(args.encoding, args.path))
        // 至少包含一个词组
        .filter { str -> WhiteList.cet.any { str.contains(it, true) } }
}
