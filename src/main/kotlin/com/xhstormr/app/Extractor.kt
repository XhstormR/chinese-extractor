package com.xhstormr.app

object Extractor {

    fun extract(args: ExtractorArgs) = when (args.lang) {
        ExtractorLang.ZH -> ChineseExtractor.extract(args)
        ExtractorLang.EN -> EnglishExtractor.extract(args)
        ExtractorLang.IP -> IPAddressExtractor.extract(args)
    }
}
