package com.xhstormr.app

object DateExtractor {

    private const val COMMAND =
        """cmd /c rg -a -o -f %s --encoding %s %s"""

    private val rulesFile = createTempFile()
        .apply { deleteOnExit() }
        .apply { writeText(WhiteList.date) }

    fun extract(args: ExtractorArgs) = args.type.charsets.associateWith { charset ->
        readProcessOutput(COMMAND.format(rulesFile, charset, args.path))
            .run { mapOf(TextType.Date to this) }
    }
}
