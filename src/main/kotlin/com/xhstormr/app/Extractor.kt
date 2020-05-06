package com.xhstormr.app

object Extractor {

    private const val COMMAND =
        """cmd /c rg "[\p{han}]{2,}" -a -o %s"""

    fun extract(args: ExtractorArgs): Set<String> {
        val path = args.path

        return readProcessOutput(COMMAND.format(path))
    }
}

/*
* iconv -f GBK -t UTF-8 -c 123.dmp | rg "[\p{han}]{2,}" -a -o | busybox sort -u
*/
