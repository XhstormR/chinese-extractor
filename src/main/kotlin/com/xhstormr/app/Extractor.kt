package com.xhstormr.app

object Extractor {

    fun extract(args: ExtractorArgs) = when (args.type) {
        ExtractorType.ZH -> ChineseExtractor.extract(args)
        ExtractorType.EN -> EnglishExtractor.extract(args)
        ExtractorType.Domain -> DomainExtractor.extract(args)
    }
}
