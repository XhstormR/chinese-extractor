package com.xhstormr.app

object Extractor {

    fun extract(args: ExtractorArgs) = when (args.type) {
        ExtractorType.All -> AllExtractor.extract(args)
        ExtractorType.ZH -> ChineseExtractor.extract(args)
        ExtractorType.EN -> EnglishExtractor.extract(args)
        ExtractorType.Date -> DateExtractor.extract(args)
        ExtractorType.Domain -> DomainExtractor.extract(args)
    }
}
