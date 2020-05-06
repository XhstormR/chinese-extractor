package com.xhstormr.app

object Converter {

    private const val COMMAND =
        """cmd /c iconv -f %s -t UTF-8 -c %s > %s"""

    fun convert(args: ConverterArgs): Set<String> {
        val (type, binPath, txtPath) = args

        return readProcessOutput(COMMAND.format(type.encoding, binPath, txtPath))
    }
}
