package com.xhstormr.app

object IPAddressExtractor {

    private const val COMMAND =
        """cmd /c rg "([0-9]{1,3}\.){3}[0-9]{1,3}" -a -o --encoding %s %s"""

    fun extract(args: ExtractorArgs) = args.type.charsets.associateWith { charset ->
        readProcessOutput(COMMAND.format(charset, args.path))
    }
}
