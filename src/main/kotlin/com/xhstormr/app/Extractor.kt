package com.xhstormr.app

object Extractor {

    private const val COMMAND =
        """cmd /c iconv -f %s -t UTF-8 -c %s | rg "[\p{han}]{2,}" -a -o"""

    fun extract(args: ExtractorArgs): Set<String> {
        val (type, path) = args

        return Runtime.getRuntime()
            .exec(COMMAND.format(type.encoding, path))
            .inputStream
            .bufferedReader()
            .useLines { it.toSet() }
    }
}

/*
* iconv -f GBK -t UTF-8 -c 123.dmp | rg "[\p{han}]{2,}" -a -o | busybox sort -u
*/
