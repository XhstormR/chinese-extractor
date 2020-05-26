package com.xhstormr.app

object EnglishExtractor {

    private const val COMMAND =
        """cmd /c rg "(?-u:[\w./\-\"\\ ]){2,}" -a -o %s"""

    fun extract(path: String) = readProcessOutput(COMMAND.format(path))
        // 至少包含一个词组
        .filter { str -> WhiteList.cet.any { str.contains(it, true) } }
}
