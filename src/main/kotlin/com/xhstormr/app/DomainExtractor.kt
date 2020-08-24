package com.xhstormr.app

object DomainExtractor {

    private const val COMMAND =
        """cmd /c rg -a -o -f %s --encoding %s %s"""

    private val rulesFile = createTempFile()
        .apply { deleteOnExit() }
        .apply { writeText(WhiteList.domain) }

    fun extract(args: ExtractorArgs) = args.type.charsets.associateWith { charset ->
        readProcessOutput(COMMAND.format(rulesFile, charset, args.path))
            .run { mapOf(TextType.Domain to this) }
    }
}

/*
* https://www.iana.org/domains/root/db
* https://publicsuffix.org/list/public_suffix_list.dat
* https://en.m.wikipedia.org/wiki/List_of_Internet_top-level_domains
* https://github.com/hankcs/HanLP/blob/1.x/src/main/java/com/hankcs/hanlp/tokenizer/URLTokenizer.java
*/
